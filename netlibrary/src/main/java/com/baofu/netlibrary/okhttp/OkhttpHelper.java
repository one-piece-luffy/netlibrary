package com.baofu.netlibrary.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.interceptor.RedirectInterceptor;
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
 * Created by wanglin on 2017/10/31.
 * 必须在application oncreate里面 先调用 init（context），必须传入applicationcontext
 */

public class OkhttpHelper {

    private static final String TAG = "OkhttpsHelper";

    private static final OkhttpHelper ourInstance = new OkhttpHelper();
    Handler mMainHandler = new Handler(Looper.getMainLooper());
    private OkHttpClient mClient;
    public static final int UNKNOW=-1;

    /**
     * Global request queue for Volley
     */

    private boolean inited;

    BPConfig config;

    public static OkhttpHelper getInstance() {
        return ourInstance;
    }

    private OkhttpHelper() {
    }

    /**
     * 参数传递 applicationContext
     *
     * @param config
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
        if (builder.encryptionUrl) {
            try {
                String url = NetUtils.decodePassword(builder.url,builder.encryptionDiff);
                builder.url = url;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, null,UNKNOW);
                return;
            }
        }
        if (!TextUtils.isEmpty(builder.appenEncryptPath)) {
            try {
                String appen = NetUtils.decodePassword(builder.appenEncryptPath,builder.encryptionDiff);
                builder.url += appen;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, null,UNKNOW);
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

    private <E> Request.Builder getBuilder(BPRequestBody<E> builder) {
        Request.Builder okBuilder = new Request.Builder();
        if (config != null && config.header != null) {

            Iterator<Map.Entry<String, String>> it = config.header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if(entry==null)
                    continue;
                String key=entry.getKey();
                String value=entry.getValue();
                if(TextUtils.isEmpty(key)||TextUtils.isEmpty(value)){
                    continue;
                }
                okBuilder.addHeader(key,value);
            }

        }
        if (builder.header != null) {
            Iterator<Map.Entry<String, String>> it = builder.header.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if(entry==null)
                    continue;
                String key=entry.getKey();
                String value=entry.getValue();
                if(TextUtils.isEmpty(key)||TextUtils.isEmpty(value)){
                    continue;
                }
                okBuilder.addHeader(key,value);
            }

        }
        okBuilder.tag(builder.requestTag);

        return okBuilder;
    }

    /**
     * post的请求参数，构造RequestBody
     *
     * @param BodyParams
     * @return
     */
    private RequestBody setRequestBody(Map<String, String> BodyParams) {
        RequestBody body = null;
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (BodyParams != null) {
            Iterator<Map.Entry<String, String>> it = BodyParams.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if(entry==null)
                    continue;
                String key=entry.getKey();
                String value=entry.getValue();
                if(TextUtils.isEmpty(key)||TextUtils.isEmpty(value)){
                    continue;
                }
                formEncodingBuilder.add(key, value);
            }

        }
        body = formEncodingBuilder.build();
        return body;

    }

    private <E> void post(BPRequestBody<E> builder) {
        RequestBody body=null;
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            body=RequestBody.create(MediaType.parse("application/json;charset=utf-8"), builder.paramsJson);
        }else {
            body = setRequestBody(builder.params);
        }

        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.post(body).url(builder.url).build();

        //3 将Request封装为Call
        Call call = mClient.newCall(request);
        //4 执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handlerError(builder, e,UNKNOW);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                int code = response.code();
                if(response==null){
                    handlerError(builder, null,code);
                    return;
                }

                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {
                    handlerError(builder, null,code);
                }

            }
        });
    }

    private <E> void get(BPRequestBody<E> builder) {
        if(builder.needCache){
            if (builder.onCacheBean != null) {
                final E model = NetSharePreference.getCache(config.context,builder.url,builder.clazz);
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
                    String res= NetSharePreference.getCacheByString(config.context,builder.url);
                    mMainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            builder.onCacheString.onCacheString(res);
                        }
                    });
                }
            }
        }

        Request.Builder okBuilder = getBuilder(builder);
        String url = builder.url;
        if (builder.params != null) {
            Iterator<Map.Entry<String, String>> it = builder.params.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                if(entry==null)
                    continue;
                String key=entry.getKey();
                String value=entry.getValue();
                if(TextUtils.isEmpty(key)||TextUtils.isEmpty(value)){
                    continue;
                }
                if (url.contains("?")) {
                    url = url + "&" + key + "=" + value;
                } else {
                    url = url + "?" + key + "=" + value;
                }
            }

        }
        Request request = okBuilder.url(url)
                .build();
        try {
            Call call = mClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(final Call call, final IOException e) {
                    handlerError(builder, e,UNKNOW);
                }

                @Override
                public void onResponse(final Call call, final Response response) throws IOException {
                    int code = response.code();
                    if(response==null){
                        handlerError(builder, null,code);
                        return;
                    }

                    if ((code >= 200 && code < 300) || code == 304) {

                        handlerResponse(call, response, builder);
                    } else {
                        handlerError(builder, null,code);
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
            public void onFailure(final Call call, final IOException e) {
                handlerError(builder, e,UNKNOW);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                int code = response.code();
                if(response==null){
                    handlerError(builder, null,code);
                    return;
                }

                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {
                    handlerError(builder, null,code);
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
            public void onFailure(final Call call, final IOException e) {
                handlerError(builder, e,-1);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                int code = response.code();
                if(response==null){
                    handlerError(builder, new IOException(),code);
                    return;
                }

                if ((code >= 200 && code < 300) || code == 304) {

                    handlerResponse(call, response, builder);
                } else {
                    handlerError(builder, new IOException(),code);
                }


            }
        });
    }

    private <E> void handlerError(BPRequestBody<E> builder, IOException e,int code) {
        if (builder.onException != null ) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    builder.onException.onException(e,code,null);
                }
            });

        }
        if(config.onResponseListener!=null){
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    config.onResponseListener.exceptionListener(builder.url,null,e,code);
                }
            });

        }
    }

    private <E> void handlerResponse(final Call call, final Response response, BPRequestBody<E> builder) {

        try {
            String json = response.body().string();

            if(config.onResponseListener!=null){
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        config.onResponseListener.responseListener(response.headers(),response.code(),builder.url,json);
                    }
                });

            }
            if (builder.onResponseBean != null) {
                E model = null;
                try {
                    model=JSON.parseObject(json, builder.clazz);
                }catch (JSONException e){
                    e.printStackTrace();
                }
                E finalModel = model;
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onResponseBean.onResponse(finalModel);
                    }
                });
                if(builder.needCache&&config!=null&&config.context!=null){
                    NetSharePreference.saveCache(config.context,builder.url,finalModel);
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

                // 报文体
                mMainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        builder.onResponse.onResponse(json, map);
                    }
                });
                if(builder.needCache&&config!=null&&config.context!=null){
                    NetSharePreference.saveCacheByString(config.context,builder.url,json);
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
                    if(builder.needCache&&config!=null&&config.context!=null){
                        NetSharePreference.saveCacheByString(config.context,builder.url,json);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            handlerError(builder, e,UNKNOW);
        }
    }


}
