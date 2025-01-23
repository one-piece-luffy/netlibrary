package com.baofu.netlib;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.baofu.netlib.bean.ConfigModelBean;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.BaseViewModel;
import com.baofu.netlibrary.BPListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Response;

public class MonitorViewModel extends BaseViewModel {
    public final String COOKIE = "jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzNhYzYxOGNmOWYzNDNkOGZkMDMyMzkiLCJpYXQiOjE2MDAzOTkzMjgsImV4cCI6MTYwMTYwODkyOH0.ImOPavAeqrTKFT-WOBmPefoKVV3c29DV1v5eZylNvXc";

    public final String TAG="MonitorViewModel";
    public MonitorViewModel(@NonNull Application application) {
        super(application);
    }


    /**
     * 异步请求
     */
    public void request() {

//        /**
//         * 请求方式1
//         */
        Map<String, String> param = new HashMap<>();
        param.put("code", "18933333333");
        param.put("password", "123456");
        Map<String, String> header = new HashMap<>();
        header.put("header", "header");
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.GET)
                .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                .setParams(param)
                .setRequestTag(System.currentTimeMillis()+"")
                .setHeader(header)
                .setNeedCache(true)


                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
                    @Override
                    public void onResponse(ConfigModelBean response) {
                        Log.e("time",response.toString()+"");
                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnCacheBean(new BPListener.onCacheBean() {
                    @Override
                    public void onCache(Object response) {
                        if(response==null)
                            return;
                        Log.e("asdf","cache time:"+response.toString()+"");
                    }
                })
                .setOnException(new BPListener.OnException() {
                    @Override
                    public void onException(Exception e, int code, String response) {
                        e.printStackTrace();
                        Toast.makeText(BaseApplication.getInstance(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }

                })
                .request();
    }
    /**
     * 同步请求返回bean
     */
    public void requestSync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> param = new HashMap<>();
                param.put("code", "18933333333");
                param.put("password", "123456");
                Map<String, String> header = new HashMap<>();
                header.put("header", "header");
                Response response= BPRequest.getInstance()
                        .setMethod(BPRequest.Method.GET)
                        .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                        .setParams(param)
                        .setRequestTag(System.currentTimeMillis()+"")
                        .setHeader(header)
                        .setNeedCache(true)
                        .setClazz(String.class)

                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
                    @Override
                    public void onResponse(ConfigModelBean response) {
                        Log.e("time",response.toString()+"");
                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
                    }
                })
                        .setOnException(new BPListener.OnException() {
                            @Override
                            public void onException(Exception e, int code, String response) {
                                e.printStackTrace();
                            }

                        })
                        .requestSync();
                ConfigModelBean result= null;
                try {
                    result = JSON.parseObject(response.body().string(), ConfigModelBean.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("asdf","sync result:"+result);
            }
        }).start();

    }

    /**
     * 获取首页tab
     */
    public void requestTab() {
        Map<String,String> header=new HashMap<>();
        header.put("source","dev");
        header.put("version","24121212");
        //更新数据
        String url = "/video/types";
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.GET)
                .setHeader(header)
                .setUrl("dKU3fGryO}jxPmHzOmH|RF7|PmH9RWj8RD@@")
                .appenPath(url)
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf",response);
                        Toast.makeText(BaseApplication.getInstance(),response,Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnException((e, code, response) ->{
                    e.printStackTrace();
                    Log.e("asdf","error");
                    Toast.makeText(BaseApplication.getInstance(),"error",Toast.LENGTH_SHORT).show();

                } )
                .request();

    }

}