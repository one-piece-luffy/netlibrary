package com.baofu.netlib;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Response;

public class MyTestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        Response response = chain.proceed(request);
        Log.e("sdf","-==intercept");
        return response;
    }
}