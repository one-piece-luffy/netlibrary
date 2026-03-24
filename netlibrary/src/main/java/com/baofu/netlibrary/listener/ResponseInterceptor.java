package com.baofu.netlibrary.listener;

import okhttp3.Headers;
import okhttp3.Response;

public interface ResponseInterceptor {
    /**
     * 响应监听（在返回给调用者之前处理）
     * @param headers 响应头
     * @param statusCode 状态码
     * @param url 请求URL
     * @param originalResponse 原始响应
     * @return 处理后的响应，如果返回null则中断请求
     */
    Response onResponseReceived(Headers headers, int statusCode, String url, Response originalResponse);

    /**
     * 异常监听
     * @param url 请求URL
     * @param errorMessage 错误信息
     * @param exception 异常
     * @param errorCode 错误码
     */
    void onExceptionOccurred(String url, String errorMessage, Exception exception, int errorCode);
}