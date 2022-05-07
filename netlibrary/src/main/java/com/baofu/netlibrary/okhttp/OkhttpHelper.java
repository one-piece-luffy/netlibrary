package com.baofu.netlibrary.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.okhttp.interceptor.RedirectInterceptor;
import com.baofu.netlibrary.utils.NetConstans;
import com.baofu.netlibrary.utils.NetUtils;
import com.baofu.netlibrary.utils.SSLUtil;
import com.baofu.netlibrary.utils.NetSharePreference;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lhy on 2017/10/31.
 * 必须在application oncreate里面 先调用 init（context），必须传入applicationcontext
 */

public class OkhttpHelper {

    private static final String TAG = "OkhttpsHelper";

    private static final OkhttpHelper ourInstance = new OkhttpHelper();
    Handler mMainHandler = new Handler(Looper.getMainLooper());
    private OkHttpClient mClient;
    public static final int UNKNOW = -1;


    BPConfig config;

    public static OkhttpHelper getInstance() {
        return ourInstance;
    }

    private OkhttpHelper() {
    }

    /**
     * 参数传递 applicationContext
     *
     */
    public void init(BPConfig config) {

        this.config = config;
        int timeout = config.timeout == 0 ? 15 : config.timeout;
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .readTimeout(timeout, TimeUnit.SECONDS)
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES))
//        配置 WebSocket 心跳间隔（默认没有心跳）
                .pingInterval(60, TimeUnit.SECONDS)
                .addInterceptor(new RedirectInterceptor())
                .hostnameVerifier(SSLUtil.getInstance().getHostnameVerifier())
                .sslSocketFactory(SSLUtil.getInstance().getSSLSocketFactory(), SSLUtil.getInstance().getTrustManager());
        if (config.interceptorList != null) {
            for (int i = 0; i < config.interceptorList.size(); i++) {
                builder.addInterceptor(config.interceptorList.get(i));
            }
        }
        if (config.banProxy) {
            builder.proxy(Proxy.NO_PROXY);
        }
        mClient = builder.build();

    }

    public void cancelRequests(Object tag) {
        Dispatcher dispatcher = mClient.dispatcher();
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : dispatcher.runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }


    public <E> void request(BPRequestBody<E> builder) {

//        if (!TextUtils.isEmpty(builder.paramsJson)) {
//            task.setBodyPara(builder.paramsJson);
//            task.bodyType(OkHttps.JSON);
//
//        }

        if (!TextUtils.isEmpty(builder.appenEncryptPath)) {
            try {
                String appen = NetUtils.decodePassword(builder.appenEncryptPath, builder.encryptionDiff);
                builder.url += appen;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, null, UNKNOW);
                return;
            }
        }


        switch (builder.method) {
            case BPRequest.Method.POST:
                post(builder);
                break;
            case BPRequest.Method.GET:
                get(builder);
                break;
            case BPRequest.Method.DELETE:
                delete(builder);
                break;
            case BPRequest.Method.PATCH:
                patch(builder);
                break;
        }
    }

    /**
     * 同步请求
     *
     */
    public <T> T requestSync(BPRequestBody<T> builder) {

        if (!TextUtils.isEmpty(builder.appenEncryptPath)) {
            try {
                String appen = NetUtils.decodePassword(builder.appenEncryptPath, builder.encryptionDiff);
                builder.url += appen;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, null, UNKNOW);
                return null;
            }
        }

        T result = null;
        switch (builder.method) {
            case BPRequest.Method.POST:
                result = postSync(builder);
                break;
            case BPRequest.Method.GET:
                result = getSync(builder);
                break;
            case BPRequest.Method.DELETE:
                result = deleteSync(builder);
                break;
            case BPRequest.Method.PATCH:
                result = patchSync(builder);
                break;
        }
        return result;
    }

    /**
     * 同步请求
     *
     */
    public String requestStringSync(BPRequestBody builder) {

        if (!TextUtils.isEmpty(builder.appenEncryptPath)) {
            try {
                String appen = NetUtils.decodePassword(builder.appenEncryptPath, builder.encryptionDiff);
                builder.url += appen;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, null, UNKNOW);
                return null;
            }
        }

        String result = null;
        switch (builder.method) {
            case BPRequest.Method.POST:
                result = postStringSync(builder);
                break;
            case BPRequest.Method.GET:
                result = getStringSync(builder);
                break;
            case BPRequest.Method.DELETE:
                result = deleteStringSync(builder);
                break;
            case BPRequest.Method.PATCH:
                result = patchStringSync(builder);
                break;
        }
        return result;
    }

    private <E> Request.Builder getBuilder(BPRequestBody<E> builder) {
        Request.Builder okBuilder = new Request.Builder();
        if (config != null && config.header != null) {

            for (Map.Entry<String, String> entry : config.header.entrySet()) {
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                okBuilder.addHeader(key, value);
            }

        }
        if (builder.header != null) {
            for (Map.Entry<String, String> entry : builder.header.entrySet()) {
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                okBuilder.addHeader(key, value);
            }

        }
        okBuilder.tag(builder.requestTag);

        return okBuilder;
    }

    /**
     * post的请求参数，构造RequestBody
     *
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body ;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (BodyParams != null) {
            for (Map.Entry<String, String> entry : BodyParams.entrySet()) {
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                formEncodingBuilder.add(key, value);
            }

        }
        body = formEncodingBuilder.build();
        return body;

    }

    private <E> void post(BPRequestBody<E> builder) {
        RequestBody body;
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            body = RequestBody.create(builder.paramsJson, MediaType.get("application/json; charset=utf-8"));
        } else {
            body = setRequestBody(builder.params);
        }

        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.post(body).url(builder.url).build();

        //3 将Request封装为Call
        Call call = mClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call, @NonNull final IOException e) {
                handlerError(builder, e, UNKNOW);
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull  final Response response) {
                int code = response.code();

                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {

                    handlerError(builder, new Exception( response.message()), code);
                }

            }
        });
    }


    private <E> void get(BPRequestBody<E> builder) {
        if (builder.needCache) {
            if (builder.onCacheBean != null) {
                final E model = NetSharePreference.getCache(config.context, builder.url, builder.clazz);
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onCacheBean.onCache(model);
                    }
                });

            } else if (builder.onResponse != null) {
                //todo

            } else {
                // 得到响应报文体的字符串 String 对象
                if (builder.onCacheString != null) {
                    String res = NetSharePreference.getCacheByString(config.context, builder.url);
                    mMainHandler.post(() -> builder.onCacheString.onCacheString(res));
                }
            }
        }

        Request.Builder okBuilder = getBuilder(builder);
        String url = builder.url;
        if (builder.params != null) {
            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();
            StringBuilder stringBuffer=new StringBuilder(url);
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (url.contains("?")) {
                    stringBuffer.append("&");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                } else {
                    stringBuffer.append("?");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                }
            }
            url=stringBuffer.toString();

        }
        Request request = okBuilder.url(url)
                .build();
        try {
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull final Call call, @NonNull final IOException e) {
                    handlerError(builder, e, UNKNOW);
                }

                @Override
                public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {
                    int code = response.code();

                    if ((code >= 200 && code < 300) || code == 304) {

                        handlerResponse(call, response, builder);
                    } else {
                        handlerError(builder,  new Exception( response.message()), code);
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <E> void delete(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();

        //3 将Request封装为Call
        Call call = mClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call, @NonNull final IOException e) {
                handlerError(builder, e, UNKNOW);
            }

            @Override
            public void onResponse(@NonNull final Call call,@NonNull final Response response)  {
                int code = response.code();

                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {
                    handlerError(builder,  new Exception( response.message()), code);
                }


            }
        });

    }

    private <E> void patch(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();

        //3 将Request封装为Call
        Call call = mClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull final Call call, @NonNull final IOException e) {
                handlerError(builder, e, UNKNOW);
            }

            @Override
            public void onResponse(@NonNull final Call call, @NonNull final Response response) throws IOException {
                int code = response.code();
                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {
                    handlerError(builder, new IOException(), code);
                }


            }
        });
    }

    private <E> E postSync(BPRequestBody<E> builder) {

        RequestBody body = null;
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            body = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), builder.paramsJson);
        } else {
            body = setRequestBody(builder.params);
        }

        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.post(body).url(builder.url).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                E model = handlerResponseSync(response, builder);
                return model;
            } else {
                handlerErrorSync(builder, null, code);
            }

        } catch (Exception e) {
            handlerErrorSync(builder, e, UNKNOW);
        }
        return null;

    }

    private <E> E getSync(BPRequestBody<E> builder) {
        if (builder.needCache) {
            if (builder.onCacheBean != null) {
                final E model = NetSharePreference.getCache(config.context, builder.url, builder.clazz);
                builder.onCacheBean.onCache(model);

            } else if (builder.onResponse != null) {
                //todo

            } else {
                // 得到响应报文体的字符串 String 对象
                if (builder.onCacheString != null) {
                    String res = NetSharePreference.getCacheByString(config.context, builder.url);
                    builder.onCacheString.onCacheString(res);
                }
            }
        }

        Request.Builder okBuilder = getBuilder(builder);
        String url = builder.url;
        if (builder.params != null) {
            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();
            StringBuilder stringBuffer=new StringBuilder(url);
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (url.contains("?")) {
                    stringBuffer.append("&");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                } else {
                    stringBuffer.append("?");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                }
            }
            url=stringBuffer.toString();
        }
        Request request = okBuilder.url(url)
                .build();
        try {
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                E model = handlerResponseSync(response, builder);
                return model;
            } else {
                handlerErrorSync(builder, null, code);
            }


        } catch (Exception e) {
            e.printStackTrace();
            handlerErrorSync(builder, e, UNKNOW);
        }
        return null;
    }

    private <E> E deleteSync(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseSync(response, builder);
            } else {
                handlerErrorSync(builder, null, code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private <E> E patchSync(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseSync(response, builder);
            } else {
                handlerErrorSync(builder, null, code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String postStringSync(BPRequestBody builder) {

        RequestBody body ;
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            body = RequestBody.create(builder.paramsJson, MediaType.get("application/json; charset=utf-8"));
        } else {
            body = setRequestBody(builder.params);
        }

        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.post(body).url(builder.url).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseStringSync(response, builder);
            } else {
                handlerErrorSync(builder, null, code);
            }

        } catch (Exception e) {
            handlerErrorSync(builder, e, UNKNOW);
        }
        return null;

    }

    private String getStringSync(BPRequestBody builder) {

        Request.Builder okBuilder = getBuilder(builder);
        String url = builder.url;
        if (builder.params != null) {

            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();
            StringBuilder stringBuffer=new StringBuilder(url);
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (url.contains("?")) {
                    stringBuffer.append("&");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                } else {
                    stringBuffer.append("?");
                    stringBuffer.append(key);
                    stringBuffer.append("=");
                    stringBuffer.append(value);
                }
            }
            url=stringBuffer.toString();

        }
        Request request = okBuilder.url(url)
                .build();
        try {
            Call call = mClient.newCall(request);
            Response response = call.execute();

            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseStringSync(response, builder);
            } else {
                handlerErrorSync(builder, null, code);
            }


        } catch (Exception e) {
            e.printStackTrace();
            handlerErrorSync(builder, e, UNKNOW);
        }
        return null;
    }

    private String deleteStringSync(BPRequestBody builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseStringSync(response, builder);
            } else {
                handlerErrorSync(builder, null, code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private String patchStringSync(BPRequestBody builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            Response response = call.execute();
            int code = response.code();
            if ((code >= 200 && code < 300) || code == 304) {
                return handlerResponseStringSync(response, builder);
            } else {
                handlerErrorSync(builder, new Exception( response.message()), code);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private <E> void handlerError(BPRequestBody<E> builder, Exception e, int code) {

        if (config.onResponseListener != null) {
            Exception exception = e;
            if (exception == null) {
                exception = new Exception("UNKNOW");
            }
            String result = config.onResponseListener.exceptionListener(builder.url, null, exception, code);
            //拦截错误，在config.exceptionListener里统一处理
            if(NetConstans.Interceptor.equals(result)){
                return;
            }
        }
        if (builder.onException != null) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Exception exception=e;
                    if(exception==null){
                        exception=new Exception("UNKNOW");
                    }
                    builder.onException.onException(exception, code, null);
                }
            });

        }
    }

    private <E> void handlerResponse(final Call call, final Response response, BPRequestBody<E> builder) {

        try {
            String json = response.body().string();

            if (config.onResponseListener != null) {
                String result = config.onResponseListener.responseListener(response.headers(), response.code(), builder.url, json);
                //拦截请求，在config.onResponseListener里统一处理
                if (NetConstans.Interceptor.equals(result)) {
                    return;
                }
            }
            if (builder.onResponseBean != null) {
                E model ;
                try {
                    model = JSON.parseObject(json, builder.clazz);
                } catch (JSONException e) {
//                    e.printStackTrace();
                    handlerError(builder,  e, UNKNOW);
                    return;
                }
                E finalModel = model;
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onResponseBean.onResponse(finalModel);
                    }
                });
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCache(config.context, builder.url, finalModel);
                }

            } else if (builder.onResponse != null) {
                // 响应回调
                int status = response.code();      // 状态码
                Headers headers = response.headers(); // 响应头
                Map<String, String> map = new HashMap<>();
                String cookie = "";

                for (int i = 0, count = headers.size(); i < count; i++) {

                    String name = headers.name(i);
                    String value = headers.value(i);
                    if (name.equalsIgnoreCase("Set-Cookie")) {
                        cookie += value;
                        if (!value.equals(";")) {
                            cookie += ";";
                        }
                    } else {
                        map.put(name, value);
                    }
                    if (!TextUtils.isEmpty(cookie)) {
                        map.put("Set-Cookie", cookie);
                    }
                }

                // 报文体
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onResponse.onResponse(json, map);
                    }
                });
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCacheByString(config.context, builder.url, json);
                }


            } else {
                // 得到响应报文体的字符串 String 对象
                if (builder.onResponseString != null) {

                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            builder.onResponseString.onResponse(json);
                        }
                    });
                    if (builder.needCache && config != null && config.context != null) {
                        NetSharePreference.saveCacheByString(config.context, builder.url, json);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            handlerError(builder, e, UNKNOW);
        }
    }

    private <E> void handlerErrorSync(BPRequestBody<E> builder, Exception e, int code) {
        if (config.onResponseListener != null) {
            Exception exception = e;
            if (exception == null) {
                exception = new Exception("UNKNOW");
            }
            String result = config.onResponseListener.exceptionListener(builder.url, null, exception, code);
            //拦截错误，在config.exceptionListener里统一处理
            if (NetConstans.Interceptor.equals(result)) {
                return;
            }
        }
        if (builder.onException != null) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Exception exception=e;
                    if(exception==null){
                        exception=new Exception("UNKNOW");
                    }
                    builder.onException.onException(exception, code, null);
                }
            });


        }

    }

    private <E> E handlerResponseSync(final Response response, BPRequestBody<E> builder) {

        try {
            String json = response.body().string();

            if (config.onResponseListener != null) {
                //todo 这里是同步的可能有问题，拦截以后会导致同步请求返回的数据都是null ，以后遇到再处理
                String result = config.onResponseListener.responseListener(response.headers(), response.code(), builder.url, json);
                //拦截请求，在config.onResponseListener里统一处理
                if (NetConstans.Interceptor.equals(result)) {
                    return null;
                }
            }

            if (builder.onResponseBean != null) {
                E model = null;
                try {
                    model = JSON.parseObject(json, builder.clazz);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCache(config.context, builder.url, model);
                }
                return model;

            } else if (builder.onResponse != null) {
                // 响应回调

                Headers headers = response.headers(); // 响应头
                Map<String, String> map = new HashMap<>();
                String cookie = "";

                for (int i = 0, count = headers.size(); i < count; i++) {

                    String name = headers.name(i);
                    String value = headers.value(i);
                    if (name.toLowerCase().equals("Set-Cookie".toLowerCase())) {
                        cookie += value;
                        if (!value.equals(";")) {
                            cookie += ";";
                        }
                    } else {
                        map.put(name, value);
                    }
                    if (!TextUtils.isEmpty(cookie)) {
                        map.put("Set-Cookie", cookie);
                    }
                }

                builder.onResponse.onResponse(json, map);
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCacheByString(config.context, builder.url, json);
                }
                return (E) json;

            } else {
                // 得到响应报文体的字符串 String 对象
                if (builder.onResponseString != null) {

                    builder.onResponseString.onResponse(json);
                    if (builder.needCache && config != null && config.context != null) {
                        NetSharePreference.saveCacheByString(config.context, builder.url, json);
                    }
                    return (E) json;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            handlerError(builder, e, UNKNOW);
        }
        return null;
    }

    private String handlerResponseStringSync(final Response response, BPRequestBody builder) {

        try {
            String json = response.body().string();

            if (config.onResponseListener != null) {

                String result = config.onResponseListener.responseListener(response.headers(), response.code(), builder.url, json);
                //拦截请求，在config.onResponseListener里统一处理
                if (NetConstans.Interceptor.equals(result)) {
                    return null;
                }
            }

            if (builder.onResponseBean != null) {
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCache(config.context, builder.url, json);
                }

            } else if (builder.onResponse != null) {
                // 响应回调

                Headers headers = response.headers(); // 响应头
                Map<String, String> map = new HashMap<>();
                String cookie = "";

                for (int i = 0, count = headers.size(); i < count; i++) {

                    String name = headers.name(i);
                    String value = headers.value(i);
                    if (name.equalsIgnoreCase("Set-Cookie")) {
                        cookie += value;
                        if (!value.equals(";")) {
                            cookie += ";";
                        }
                    } else {
                        map.put(name, value);
                    }
                    if (!TextUtils.isEmpty(cookie)) {
                        map.put("Set-Cookie", cookie);
                    }
                }

                builder.onResponse.onResponse(json, map);
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCacheByString(config.context, builder.url, json);
                }

            } else {
                // 得到响应报文体的字符串 String 对象
                if (builder.onResponseString != null) {

                    builder.onResponseString.onResponse(json);
                    if (builder.needCache && config != null && config.context != null) {
                        NetSharePreference.saveCacheByString(config.context, builder.url, json);
                    }
                }
            }
            return json;
        } catch (IOException e) {
            e.printStackTrace();
            handlerError(builder, e, UNKNOW);
        }
        return null;
    }

}
