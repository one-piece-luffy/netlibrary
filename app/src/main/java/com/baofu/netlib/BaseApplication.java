package com.baofu.netlib;

import android.app.Application;
import android.content.Context;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequest;
import com.example.netlibrary.okhttps.OkhttpsHelper;
import com.example.netlibrary.volley.VolleyHelper;

import java.util.HashMap;
import java.util.Map;

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
        BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.VOLLEY).addHeader(header).build();
        BPRequest.getInstance().init(config);
    }
}