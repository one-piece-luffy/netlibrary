package com.baofu.netlibrary.okhttp;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.okhttp.interceptor.RedirectInterceptor;
import com.baofu.netlibrary.utils.ErrorCode;
import com.baofu.netlibrary.utils.NetSharePreference;
import com.baofu.netlibrary.utils.SSLUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.Proxy;
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

    // 使用WeakReference避免Handler内存泄漏
    private WeakReference<Handler> mMainHandlerRef;
    private OkHttpClient mClient;
    public static final int UNKNOW = -1;

    final Object mQueueLock = new Object();
    BPConfig config;

    private static class Holder {
        private static final OkhttpHelper INSTANCE = new OkhttpHelper();
    }

    public static OkhttpHelper getInstance() {
        return Holder.INSTANCE;
    }

    private OkhttpHelper() {
        mMainHandlerRef = new WeakReference<>(new Handler(Looper.getMainLooper()));
    }
    private Handler getMainHandler() {
        Handler handler = mMainHandlerRef.get();
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
            mMainHandlerRef = new WeakReference<>(handler);
        }
        return handler;
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
        if (mClient == null) return;

        Dispatcher dispatcher = mClient.dispatcher();

        // 取消排队中的请求
        for (Call call : dispatcher.queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }

        // 取消运行中的请求
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


        switch (builder.method) {
            case BPRequest.Method.POST:
                post(builder);
                break;
            case BPRequest.Method.GET:
            case BPRequest.Method.HEAD:
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
    public <E> Response requestSync(BPRequestBody<E> builder) {
        if (builder == null || TextUtils.isEmpty(builder.url)) {
            handleError(builder, new Exception("url 不能为空"), ErrorCode.ERROR_URL_EMPTY);
            return null;
        }

        if (!builder.url.startsWith("http://") && !builder.url.startsWith("https://")) {
            handleError(builder, new Exception("url必须是http或者https开头"), ErrorCode.ERROR_URL_INVALID);
            return null;
        }

        if (!TextUtils.isEmpty(builder.appenPath)) {
            builder.url += builder.appenPath;
        }



        try {
            switch (builder.method) {
                case BPRequest.Method.POST:
                    return postSync(builder);
                case BPRequest.Method.GET:
                case BPRequest.Method.HEAD:
                    return getSync(builder);
                case BPRequest.Method.DELETE:
                    return deleteSync(builder);
                case BPRequest.Method.PATCH:
                    return patchSync(builder);
                default:
                    return getSync(builder);
            }
        } catch (Exception e) {
            handleError(builder, e, ErrorCode.ERROR_UNKNOWN);
            return null;
        }
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
    private RequestBody setRequestBody(Map<String, String> params) {
        okhttp3.FormBody.Builder formEncodingBuilder = new okhttp3.FormBody.Builder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry != null && !TextUtils.isEmpty(entry.getKey()) && !TextUtils.isEmpty(entry.getValue())) {
                    formEncodingBuilder.add(entry.getKey(), entry.getValue());
                }
            }
        }
        return formEncodingBuilder.build();
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
        executeRequest(request, builder);
    }


    private <E> void get(BPRequestBody<E> builder) {
        if (builder.needCache) {
            handleCache(builder);
        }

        Request.Builder okBuilder = getBuilder(builder);
        String url = buildUrlWithParams(builder.url, builder.params);

        Request request = builder.method == BPRequest.Method.HEAD
                ? okBuilder.url(url).head().build()
                : okBuilder.url(url).build();

        executeRequest(request, builder);

    }


    private <E> void delete(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();
        executeRequest(request, builder);

    }

    private <E> void patch(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();

        executeRequest(request, builder);
    }

    /**
     * 同步GET请求
     */
    private <E> Response getSync(BPRequestBody<E> builder) {

        Request.Builder okBuilder = getBuilder(builder);
        String url = buildUrlWithParams(builder.url, builder.params);

        Request request;
        if (builder.method == BPRequest.Method.HEAD) {
            request = okBuilder.url(url).head().build();
        } else {
            request = okBuilder.url(url).build();
        }
        return executeSyncRequest(request, builder);
    }

    private <E> Response postSync(BPRequestBody<E> builder) {

        RequestBody body;
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            body = RequestBody.create(builder.paramsJson,
                    MediaType.get("application/json; charset=utf-8"));
        } else {
            body = setRequestBody(builder.params);
        }

        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.post(body).url(builder.url).build();

        return executeSyncRequest(request, builder);
    }


    private <E> Response deleteSync(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).delete(body).build();
        return executeSyncRequest(request, builder);
    }

    private <E> Response patchSync(BPRequestBody<E> builder) {
        RequestBody body = setRequestBody(builder.params);
        Request.Builder okBuilder = getBuilder(builder);
        Request request = okBuilder.url(builder.url).patch(body).build();
        return executeSyncRequest(request, builder);
    }

    /**
     * 执行同步请求（统一处理）
     */
    private <E> Response executeSyncRequest(Request request, BPRequestBody<E> builder) {
        Call call = null;
        Response response = null;

        try {
            call = mClient.newCall(request);
            response = call.execute();

            // 应用响应拦截器
            if (config.responseInterceptor != null) {
                Response intercepted = config.responseInterceptor.onResponseReceived(
                        response.headers(), response.code(), builder.url, response);
                if (intercepted == null) {
                    // 拦截器返回null，中断处理
                    return null;
                }
                response = intercepted;
            }

            // 检查响应状态
            int code = response.code();
            if (code >= 200 && code < 300 || code == 304) {
                return response;
            } else {
                Exception httpError = new Exception("HTTP error: " + response.message());
                handleError(builder, httpError, code);
                return null;
            }

        } catch (IOException e) {
            handleError(builder, e, getErrorCode(e));

            return null;
        } finally {
            // 注意：不要在这里关闭response，因为需要返回给调用者
            if (response == null && call != null) {
                call.cancel();
            }
        }
    }




    // 统一处理请求
    private <E> void executeRequest(Request request, BPRequestBody<E> builder) {
        Call call = mClient.newCall(request);
        call.enqueue(createCallback(builder));
    }

    // 创建统一的回调
    private <E> Callback createCallback(BPRequestBody<E> builder) {
        return new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                int errorCode = getErrorCode(e);
                handleError(builder, e, errorCode);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                handleResponse(response, builder);
            }
        };
    }

    // 统一处理响应
    private <E> void handleResponse(Response response, BPRequestBody<E> builder) {
        try (Response resp = response) { // try-with-resources自动关闭
            // 应用拦截器
            if (config.responseInterceptor != null) {
                Response intercepted = config.responseInterceptor.onResponseReceived(
                        response.headers(), response.code(), builder.url, response);
                if (intercepted == null) {
                    // 拦截器返回null，中断处理
                    return;
                }
            }

            int code = response.code();
            if (code >= 200 && code < 300 || code == 304) {
                processResponseBody(response, builder);
            } else {
                handleError(builder, new Exception("HTTP error: " + response.message()), code);
            }
        } catch (Exception e) {
            handleError(builder, e, ErrorCode.ERROR_RESPONSE_EMPTY);
        }

    }

    // 处理响应体（支持多种回调类型）
    private <E> void processResponseBody(Response response, BPRequestBody<E> builder) {
        try {
            String json = response.body().string();

            // 优先处理原始Response回调
            if (builder.onResponse != null) {
                postToMainThread(() -> builder.onResponse.onResponse(response));
                return;
            }

            // 处理String回调
            if (builder.onResponseString != null) {
                postToMainThread(() -> builder.onResponseString.onResponse(json));
                if (builder.needCache && config != null && config.context != null) {
                    NetSharePreference.saveCacheByString(config.context, builder.url, json);
                }
                return;
            }

            // 处理Bean回调
            if (builder.onResponseBean != null && builder.clazz != null) {
                try {
                    E model = JSON.parseObject(json, builder.clazz);
                    postToMainThread(() -> builder.onResponseBean.onResponse(model));
                    if (builder.needCache && config != null && config.context != null) {
                        NetSharePreference.saveCache(config.context, builder.url, model);
                    }
                } catch (JSONException e) {
                    handleError(builder, e, ErrorCode.ERROR_JSON_PARSE);
                }
            }
        } catch (IOException e) {
            handleError(builder, e, ErrorCode.ERROR_RESPONSE_EMPTY);
        }
    }

    // 统一处理错误
    private <E> void handleError(BPRequestBody<E> builder, Exception e, int code) {
        if (config.responseInterceptor != null) {
            config.responseInterceptor.onExceptionOccurred(
                    builder.url, e != null ? e.getMessage() : null, e, code);
        }

        final Exception finalException = e != null ? e : new Exception("Unknown error");
        final int finalCode = code;

        if (builder.onException != null) {
            postToMainThread(() -> builder.onException.onException(finalException, finalCode, null));
        }

        // 添加日志记录
        Log.e(TAG, "Request failed: url=" + builder.url + ", code=" + code +
                ", error=" + finalException.getMessage());

    }


    // 获取错误码
    private int getErrorCode(IOException e) {
        if (e.getMessage() != null && e.getMessage().contains("timeout")) {
            return ErrorCode.ERROR_TIMEOUT;
        }
        if (e.getMessage() != null && e.getMessage().contains("canceled")) {
            return ErrorCode.ERROR_CANCELED;
        }
        return ErrorCode.ERROR_NETWORK;
    }

    // 线程安全的主线程执行
    private void postToMainThread(Runnable runnable) {
        Handler handler = getMainHandler();
        if ( Looper.myLooper() != Looper.getMainLooper()) {
            handler.post(runnable);
        } else {
            runnable.run();
        }
    }

    // 处理缓存
    private <E> void handleCache(BPRequestBody<E> builder) {
        if (builder.onCacheBean != null && builder.clazz != null) {
            E cache = NetSharePreference.getCache(config.context, builder.url, builder.clazz);
            if (cache != null) {
                postToMainThread(() -> builder.onCacheBean.onCache(cache));
            }
        } else if (builder.onCacheString != null) {
            String cache = NetSharePreference.getCacheByString(config.context, builder.url);
            if (cache != null) {
                postToMainThread(() -> builder.onCacheString.onCacheString(cache));
            }
        }
    }

    // 构建带参数的URL
    private String buildUrlWithParams(String baseUrl, Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return baseUrl;
        }

        StringBuilder url = new StringBuilder(baseUrl);
        boolean hasQuery = baseUrl.contains("?");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry == null) continue;
            String key = entry.getKey();
            String value = entry.getValue();
            if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) continue;

            if (hasQuery) {
                url.append("&");
            } else {
                url.append("?");
                hasQuery = true;
            }
            url.append(key).append("=").append(value);
        }

        return url.toString();
    }
}
