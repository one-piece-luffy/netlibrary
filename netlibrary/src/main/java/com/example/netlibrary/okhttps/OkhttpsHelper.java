package com.example.netlibrary.okhttps;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.ejlchina.okhttps.FastjsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.HttpTask;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.OnCallback;
import com.ejlchina.okhttps.internal.AsyncHttpTask;
import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequest;
import com.example.netlibrary.BPRequestBody;
import com.example.netlibrary.volley.RedirectInterceptor;
import com.example.netlibrary.SSLUtil;

import java.io.File;
import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.OkHttpClient;

/**
 * Created by wanglin on 2017/10/31.
 * 必须在application oncreate里面 先调用 init（context），必须传入applicationcontext
 */

public class OkhttpsHelper {

    private static final String TAG = "OkhttpsHelper";

    private static final OkhttpsHelper ourInstance = new OkhttpsHelper();

    /**
     * Global request queue for Volley
     */

    private boolean inited;

    HTTP http;
    BPConfig config;

    public static OkhttpsHelper getInstance() {
        return ourInstance;
    }

    private OkhttpsHelper() {
    }

    /**
     * 参数传递 applicationContext
     *
     * @param config
     */
    public void init(BPConfig config) {
//        if (mRequestQueue == null) {
//        }
        Handler handler = new Handler(Looper.getMainLooper());
        this.config=config;
        http = HTTP.builder()
                .responseListener((HttpTask<?> task, HttpResult result) -> {
                    // 所有请求响应后都会走这里

                    // 返回 true 表示继续执行 task 的 OnResponse 回调，
                    // 返回 false 则表示不再执行，即 阻断
                    Log.i("tag", "所有请求响应后都会走这里:");
                    if(config.onResponseListener!=null){
                        config.onResponseListener.responseListener(result.getHeaders(),result.getStatus(),task.getUrl());
                    }
                    return true;
                })
                .completeListener((HttpTask<?> task, HttpResult.State state) -> {
                    // 所有请求执行完都会走这里

                    // 返回 true 表示继续执行 task 的 OnComplete 回调，
                    // 返回 false 则表示不再执行，即 阻断
                    return true;
                })
                .exceptionListener((HttpTask<?> task, IOException error) -> {
                    // 所有请求发生异常都会走这里

                    // 返回 true 表示继续执行 task 的 OnException 回调，
                    // 返回 false 则表示不再执行，即 阻断
                    if(config.onResponseListener!=null){
                        config.onResponseListener.exceptionListener(task.getUrl(),error.getMessage());
                    }
                    return true;
                })
                .config((OkHttpClient.Builder builder) -> {
                    // 其它配置: CookieJar、SSL、缓存、代理、事件监听...
                    // 所有 OkHttp 能配置的，都可以在这里配置

                    // 配置连接池 最小10个连接（不配置默认为 5）
                    builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
                    // 配置连接超时时间（默认10秒）
                    builder.connectTimeout(20, TimeUnit.SECONDS);
                    // 配置 WebSocket 心跳间隔（默认没有心跳）
                    builder.pingInterval(60, TimeUnit.SECONDS);

                    // 配置拦截器
                    builder.addInterceptor(new RedirectInterceptor());
                    if(config.interceptorList!=null){
                        for(int i=0;i<config.interceptorList.size();i++){
                            builder.addInterceptor(config.interceptorList.get(i));
                        }
                    }
                    if(config.banProxy){
                        builder.proxy(Proxy.NO_PROXY);
                    }
                    // 缓存目录
//                    File file = new File(Environment.getExternalStorageDirectory(), "a_cache");
//                    // 缓存大小
//                    int cacheSize = 10 * 1024 * 1024;
//                    builder.cache(new Cache(file, cacheSize));

                    builder.sslSocketFactory(SSLUtil.getInstance().getSSLSocketFactory(), SSLUtil.getInstance().getTrustManager());
                    builder.hostnameVerifier( SSLUtil.getInstance().getHostnameVerifier());
                })
                .addMsgConvertor(new FastjsonMsgConvertor())
                .callbackExecutor((Runnable run) -> {
                    handler.post(run); // 在主线程执行
                })
//                .addMsgConvertor(new MsgConvertor.FormConvertor(convertor));
                .build();

    }

    public void cancelRequests(Object tag) {
        int count = http.cancel(tag.toString());  //（2）（3）（4）（6）被取消（取消标签包含"B"的任务）
        Log.i(TAG, "cancelRequests count:" + count);   // 输出 4
    }



    public <E> void request(BPRequestBody<E> builder) {
        AsyncHttpTask task = http.async(builder.url)
                .addHeader(builder.header)
                .tag(builder.requestTag)
                .setOnException((IOException e) -> {
                    // 这里处理请求异常

                    if (builder.onException != null) {
                        builder.onException.onException(e.getMessage());

                    }
                });
        if(config!=null&&config.header!=null){
            task .addHeader(config.header);
        }
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            task.setBodyPara(builder.paramsJson);
            task.bodyType(OkHttps.JSON);

        }


        if (builder.onResponseBean != null) {
            task.setOnResBean(builder.clazz, new OnCallback<E>() {
                @Override
                public void on(E data) {

                    builder.onResponseBean.onResponse(data);

                }
            });
        } else if (builder.onResponse != null) {
            task.setOnResponse((HttpResult res) -> {
                // 响应回调
                int status = res.getStatus();       // 状态码
                Headers headers = res.getHeaders(); // 响应头
                Map<String, String> map = new HashMap<>();
                String cookie="";

                for (int i = 0, count = headers.size(); i < count; i++) {

                    String name = headers.name(i);
                    String value=headers.value(i);
                    if(name.toLowerCase().equals("Set-Cookie".toLowerCase())){
                        cookie+=value;
                        if(!value.equals(";")){
                            cookie+=";";
                        }
                    }else {
                        map.put(name, value);
                    }
                    if(!TextUtils.isEmpty(cookie)){
                        map.put("Set-Cookie", cookie);
                    }
                }
                HttpResult.Body body = res.getBody();          // 报文体

                builder.onResponse.onResponse(body.toString(), map);


            });
        } else {
            task.setOnResString((String str) -> {
                // 得到响应报文体的字符串 String 对象
                if (builder.onResponseString != null) {
                    builder.onResponseString.onResponse(str);

                }
            });
        }

        switch (builder.method) {
            case BPRequest.Method.POST:
                if (builder.params != null) {

                    task.addBodyPara(builder.params);
                }
                task.post();
                break;
            case BPRequest.Method.GET:
                if (builder.params != null) {

                    task.addUrlPara(builder.params);
                }

                task.get();
                break;
            case BPRequest.Method.DELETE:
                if (builder.params != null) {
                    task.addBodyPara(builder.params);
                }
                task.delete();
                break;
        }
    }



}
