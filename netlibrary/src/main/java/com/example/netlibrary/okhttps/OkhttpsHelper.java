package com.example.netlibrary.okhttps;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.ejlchina.okhttps.GsonMsgConvertor;
import com.ejlchina.okhttps.HTTP;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.HttpTask;
import com.ejlchina.okhttps.OkHttps;
import com.ejlchina.okhttps.OnCallback;
import com.ejlchina.okhttps.internal.AsyncHttpTask;
import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequest;
import com.example.netlibrary.volley.RedirectInterceptor;
import com.example.netlibrary.BPListener;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
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
        Handler handler=new Handler(Looper.getMainLooper());
        http = HTTP.builder()
                .responseListener((HttpTask<?> task, HttpResult result) -> {
                    // 所有请求响应后都会走这里

                    // 返回 true 表示继续执行 task 的 OnResponse 回调，
                    // 返回 false 则表示不再执行，即 阻断
                    Log.e("tag", "所有请求响应后都会走这里");
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
                    return true;
                })
                .config((OkHttpClient.Builder builder) -> {
                    // 配置连接池 最小10个连接（不配置默认为 5）
                    builder.connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES));
                    // 配置连接超时时间（默认10秒）
                    builder.connectTimeout(20, TimeUnit.SECONDS);
                    // 配置 WebSocket 心跳间隔（默认没有心跳）
                    builder.pingInterval(60, TimeUnit.SECONDS);
                    // 配置拦截器
                    builder.addInterceptor(new RedirectInterceptor());
                    // 其它配置: CookieJar、SSL、缓存、代理、事件监听...
                    // 所有 OkHttp 能配置的，都可以在这里配置
                })
                .addMsgConvertor(new GsonMsgConvertor())
                .callbackExecutor((Runnable run) -> {
                    handler.post(run); // 在主线程执行
                })
//                .addMsgConvertor(new MsgConvertor.FormConvertor(convertor));
                .build();

    }


    public <E> void requestGson(int method, final String url, final Map<String, String> header, final Map<String, String> param, Class<E> mClass
            , String requestTag, boolean needCache, final BPListener.OnResponseListener<E> listener) {
        AsyncHttpTask task = http.async(url)
                .addHeader(header)

                .tag(requestTag)
                .setOnResBean(mClass, new OnCallback<E>() {
                    @Override
                    public void on(E data) {
                        if (listener != null) {
                            listener.onResponse(data);

                        }
                    }
                })
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    if (listener != null) {
                        listener.onErrorResponse(e.getMessage());

                    }
                });
        switch (method) {
            case BPRequest.Method.POST:
                task.addBodyPara(param);
                task.post();
                break;
            case BPRequest.Method.GET:
                task.addUrlPara(param);
                task.get();
                break;
        }
    }

    public <E> void requestString(int method, final String url, final Map<String, String> header, final Map<String, String> param,
                                  String requestTag, boolean needCache, final BPListener.OnResponseStrListener listener) {
        AsyncHttpTask task = http.async(url)
                .addHeader(header)
                .tag(requestTag)
                .setOnResString((String str) -> {
                    // 得到响应报文体的字符串 String 对象
                    if (listener != null) {
                        listener.onResponse(str);

                    }
                })

                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    if (listener != null) {
                        listener.onErrorResponse(e.getMessage());

                    }
                });
        switch (method) {
            case BPRequest.Method.POST:
                task.addBodyPara(param);
                task.post();
                break;
            case BPRequest.Method.GET:
                task.addUrlPara(param);
                task.get();
                break;
        }
        Log.e(TAG,"from okhttps");
    }

    public <E> void requestJsonRequest(int method, final String url, final Map<String, String> header, final JSONObject paramJsonObject,
                                       String requestTag, boolean needCache, final BPListener.OnResponseStrListener listener) {
        AsyncHttpTask task = http.async(url)
                .addHeader(header)
                .tag(requestTag)
                .bodyType(OkHttps.JSON)
//                .addBodyPara(mapType)
                .setBodyPara(paramJsonObject.toString())
                .setOnResString((String str) -> {
                    // 得到响应报文体的字符串 String 对象
                    if (listener != null) {
                        listener.onResponse(str);

                    }
                })
                .setOnException((IOException e) -> {
                    // 这里处理请求异常
                    if (listener != null) {
                        listener.onErrorResponse(e.getMessage());

                    }
                });
        switch (method) {
            case BPRequest.Method.POST:
                task.post();
                break;
            case BPRequest.Method.GET:
                task.get();
                break;
        }
    }


    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String, String> header, final JSONObject paramJsonObject,
                                            String requestTag, boolean needCache, final BPListener.OnResponseStrListener listener) {
        requestJsonRequest(method, url, header, paramJsonObject, requestTag, needCache, listener);
    }

    public <E> void requestForm(int method, final String url, final Map<String, String> header, final Map<String, String> param,
                                String requestTag, boolean needCache, final BPListener.OnResponseStrListener listener) {
    }


    private void initHeader(Map<String, String> headers) {
    }

    public void cancelRequests(Object tag) {
        int count = http.cancel(tag.toString());  //（2）（3）（4）（6）被取消（取消标签包含"B"的任务）
        Log.i(TAG, "cancelRequests count:" + count);   // 输出 4
    }

}
