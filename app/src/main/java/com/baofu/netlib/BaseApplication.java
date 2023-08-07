package com.baofu.netlib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.listener.RequestListener;
import com.baofu.netlibrary.utils.NetConstans;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        Map<String, String> header = new ConcurrentHashMap<>();
        header.put("User-Agent", "UA");
        BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTP).addHeader(header)
                .addInterceptor(new MyTestInterceptor())
//                .banProxy(true)
                .setRequestListener(new RequestListener() {
                    @Override
                    public String responseListener(Headers headers, int status, String url,String response) {
                        //这里是子线程
                        Log.e("asdf","所有的请求");
//                        Log.e("asdf","status:"+status);
//                        Log.e("asdf","url:"+url);
//                        Log.e("asdf","response:"+response);

                        //返回NetConstans.Interceptor表示拦截请求，不再向下传递，在这里统一处理
//                        return NetConstans.Interceptor;
                        return response;
                    }

                    @Override
                    public String exceptionListener(String url, String error, Exception e, int code) {
                         //这里是子线程

                        //拦截错误，不再向下传递，在这里统一处理
//                        return NetConstans.Interceptor;
                        //返回null表示不拦截
                        return null;
                    }

                })
                .build();
        BPRequest.getInstance().init(config);
    }
}