package com.baofu.netlib;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baofu.netlib.bean.ConfigModelBean;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.BaseViewModel;
import com.baofu.netlibrary.BPListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Response;

public class MonitorViewModel extends BaseViewModel {
    public final String COOKIE = "jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzNhYzYxOGNmOWYzNDNkOGZkMDMyMzkiLCJpYXQiOjE2MDAzOTkzMjgsImV4cCI6MTYwMTYwODkyOH0.ImOPavAeqrTKFT-WOBmPefoKVV3c29DV1v5eZylNvXc";

    public final String TAG = "MonitorViewModel";

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
        header.put("header1", "header1");
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.GET)
                .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                .setParams(param)
                .setRequestTag(System.currentTimeMillis() + "")
                .setHeader(header)
                .setNeedCache(true)


                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf",response);
                    }
                })
//                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
//                    @Override
//                    public void onResponse(ConfigModelBean response) {
//                        Log.e("time", response.toString() + "");
//                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
//                    }
//                })
                .setOnCacheBean(new BPListener.onCacheBean() {
                    @Override
                    public void onCache(Object response) {
                        if (response == null)
                            return;
                        Log.e("asdf", "cache time:" + response.toString() + "");
                    }
                })
                .setOnException(new BPListener.OnException() {
                    @Override
                    public void onException(Exception e, int code, String response) {
                        e.printStackTrace();
                        Toast.makeText(BaseApplication.getInstance(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
                Response response = BPRequest.getInstance()
                        .setMethod(BPRequest.Method.GET)
                        .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                        .setParams(param)
                        .setRequestTag(System.currentTimeMillis() + "")
                        .setHeader(header)
                        .setNeedCache(true)
                        .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
                            @Override
                            public void onResponse(ConfigModelBean response) {
                                Log.e("asdf", response.toString() + "");
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
                ConfigModelBean result = null;
                try {
                    result = JSON.parseObject(response.body().string(), ConfigModelBean.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("asdf", "sync result:" + result);
            }
        }).start();

    }

    public void request301() {
//        if(true){
//            String text = "这里有123个数字456和一些其他字符789";
//            Pattern pattern = Pattern.compile("\\d+"); // 编译正则表达式
//            Matcher matcher = pattern.matcher(text); // 创建matcher对象
//
//            while (matcher.find()) { // 查找匹配项
//                Log.e("asdf","originCover:"+matcher.group());
//            }
////            if (matcher.find()){
////                String cover=matcher.group();
////                Log.e("asdf","originCover:"+cover);
////            }else {
////                Log.e("asdf","no find");
////            }
//            return;
//        }


        //更新数据
        String url = "https://downloadgram.net/wp-json/visolix/api/download";
        String insUrl="https://www.instagram.com/p/DIsynCUoJ8t/?utm_source=ig_web_copy_link";
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("captcha_response","");
        jsonObject.put("format","");
        jsonObject.put("url",insUrl);
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent"
                , "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.97 Safari/537.36 Core/1.116.485.400 QQBrowser/13.6.6321.400");
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.POST)
                .setHeader(header)
                .setUrl(url)
                .setParamsJson(jsonObject.toJSONString())
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf", response);
                        Toast.makeText(BaseApplication.getInstance(), "suc", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnException((e, code, response) -> {
                    e.printStackTrace();
                    Log.e("asdf", "error:"+e.getMessage());
                    Toast.makeText(BaseApplication.getInstance(), "error", Toast.LENGTH_SHORT).show();

                })
                .request();

    }

}