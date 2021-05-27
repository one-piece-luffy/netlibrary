package com.baofu.netlib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequest;
import com.example.netlibrary.listener.RequestListener;
import com.example.netlibrary.okhttps.OkhttpsHelper;
import com.example.netlibrary.volley.VolleyHelper;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Headers;

public class BaseApplication extends Application{
    public static BaseApplication instance;
    public static BaseApplication getInstance(){
        if(instance==null){
            instance=new BaseApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "UA");
        BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTPS).addHeader(header)
                .addInterceptor(new MyTestInterceptor())
//                .banProxy(true)
                .setRequestListener(new RequestListener() {
                    @Override
                    public void responseListener(Headers headers, int status, String response, String url) {
                        Log.e("asdf","所有的请求");
                        Log.e("asdf","status:"+status);
                        Log.e("asdf","url:"+url);
                        Log.e("asdf","response:"+response);
                    }

                    @Override
                    public void exceptionListener(String url, String error) {

                    }
                })
                .build();
        BPRequest.getInstance().init(config);
    }
}