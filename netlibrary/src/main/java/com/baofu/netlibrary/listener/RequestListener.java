package com.baofu.netlibrary.listener;

import okhttp3.Headers;

public interface RequestListener {
    String responseListener(Headers headers, int status  , String url,String response);
    String exceptionListener(String url,String error,Exception e,int code);
}
