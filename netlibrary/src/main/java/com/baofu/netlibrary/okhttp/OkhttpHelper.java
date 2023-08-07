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
import com.baofu.netlibrary.utils.NetSharePreference;
import com.baofu.netlibrary.utils.NetUtils;
import com.baofu.netlibrary.utils.SSLUtil;

import java.io.IOException;
import java.net.Proxy;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.Dispatcher;
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

    final Object mQueueLock = new Object();
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
    public Response requestSync(BPRequestBody builder) {

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

        Response result = null;
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


    private <E> Request.Builder getBuilder(BPRequestBody<E> builder) {
        Request.Builder okBuilder = new Request.Builder();
        synchronized (mQueueLock){
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
                    handlerError(builder, new Exception(response.message()), code);
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
        StringBuilder url = new StringBuilder(builder.url);
        if (builder.params != null) {
            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();

            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (url.toString().contains("?")) {
                    url.append("&");
                    url.append(key);
                    url.append("=");
                    url.append(value);
                } else {
                    url.append("?");
                    url.append(key);
                    url.append("=");
                    url.append(value);
                }
            }

        }
        Request request = okBuilder.url(url.toString())
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
                        handlerError(builder,  new Exception(response.message()), code);
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
                    handlerError(builder,  new Exception(response.message()), code);
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
                    handlerError(builder,  new Exception(response.message()), code);
                }


            }
        });
    }

    private Response postSync(BPRequestBody builder) {

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
            return call.execute();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private Response getSync(BPRequestBody builder) {

        Request.Builder okBuilder = getBuilder(builder);
        StringBuilder url = new StringBuilder(builder.url);
        if (builder.params != null) {
            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if (entry == null)
                    continue;
                String key = entry.getKey();
                String value = entry.getValue();
                if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
                    continue;
                }
                if (url.toString().contains("?")) {
                    url.append("&");
                    url.append(key);
                    url.append("=");
                    url.append(value);
                } else {
                    url.append("?");
                    url.append(key);
                    url.append("=");
                    url.append(value);
                }
            }
        }
        Request request = okBuilder.url(url.toString())
                .build();
        try {
            Call call = mClient.newCall(request);
            return call.execute();

        } catch (Exception e) {
            e.printStackTrace();
            handlerErrorSync(builder, e, UNKNOW);
        }
        return null;
    }

    private Response deleteSync(BPRequestBody builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            return call.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private Response patchSync(BPRequestBody builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();

        try {
            //3 将Request封装为Call
            Call call = mClient.newCall(request);
            return call.execute();

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
            if (builder.onResponse != null) {

                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onResponse.onResponse(response);
                    }
                });
                return;


            }
            String json = response.body().string();
            if (config.onResponseListener != null) {
                String result = config.onResponseListener.responseListener(response.headers(), response.code(), builder.url, json);
                //拦截请求，在config.onResponseListener里统一处理
                if (NetConstans.Interceptor.equals(result)) {
                    return;
                }
            }
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
            if (builder.onResponseBean != null) {
                E model ;
                try {
                    model = JSON.parseObject(json, builder.clazz);
                } catch (JSONException e) {
//                    e.printStackTrace();
                    handlerError(builder, e, UNKNOW);
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

            }
        } catch (IOException e) {
            e.printStackTrace();
            handlerError(builder, e, UNKNOW);
        }
    }

    private  void handlerErrorSync(BPRequestBody builder, Exception e, int code) {
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

}
