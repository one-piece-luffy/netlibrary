package com.baofu.netlibrary.listener;

import okhttp3.Headers;
import okhttp3.Response;

public interface ResponseInterceptor {
    Response responseListener(Headers headers, int status  , String url, Response originalResponse);
    Exception exceptionListener(String url,String error,Exception e,int code);
}
