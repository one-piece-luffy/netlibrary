package com.baofu.netlib;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.baofu.netlib.bean.ConfigModelBean;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.BaseViewModel;
import com.baofu.netlibrary.BPListener;

import java.util.HashMap;
import java.util.Map;

public class MonitorViewModel extends BaseViewModel {
    public final String COOKIE = "jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1YzNhYzYxOGNmOWYzNDNkOGZkMDMyMzkiLCJpYXQiOjE2MDAzOTkzMjgsImV4cCI6MTYwMTYwODkyOH0.ImOPavAeqrTKFT-WOBmPefoKVV3c29DV1v5eZylNvXc";

    public final String TAG="MonitorViewModel";
    public MonitorViewModel(@NonNull Application application) {
        super(application);
    }



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
                        Log.e("asdf", response);
                    }

                })
                .request();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mBaseModel.cancelRequests(mRequestTag);
//            }
//        },300);

//
    }

    public void requestcookie(){
        String url =  "http://www.yeens.xyz/api.php/v1.Auth/Third";
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "UA");
        header.put("source", "newgimy");
        header.put("uniqueID", Build.ID);
        header.put("x-version", "11");
        header.put("x-os", String.format("%s %s", "Android", Build.VERSION.RELEASE));
        header.put("x-country", "zh-cn");
        header.put("x-lang",  "zh-cn");
        header.put("x-device", Build.ID);

        Map<String, String> param = new HashMap<>();
        param.put("openid", Build.ID);


       BPRequest.getInstance()
                .setMethod(BPRequest.Method.POST)
                .setUrl(url)
                .setRequestTag(mRequestTag)
                .setHeader(header)
                .setParams(param)
                .setOnResponse(new BPListener.OnResponse() {
                    @Override
                    public void onResponse(String response, Map<String,String> obj) {
                        Map<String,String> header= (Map<String, String>) obj;
                        String cookie=obj.get("Set-Cookie");
                        Map<String, String> he = new HashMap<>();
                        he.put("timestamp", System.currentTimeMillis() + "");
//                        he.put("cookie",cookie);
                        he.put("cookie","user_id=50358;user_name=644164976769;user_nick_name=TGNC4;group_id=2;group_name=%E9%BB%98%E8%AE%A4%E4%BC%9A%E5%91%98;user_check=c574b5b1871e2af45c99dd320d9a678c;user_portrait=%2Fstatic%2Fimages%2Ftouxiang.png;");
                        delete(he);
                    }
                })

                .setOnException(new BPListener.OnException() {
                    @Override
                    public void onException(Exception e, int code, String response) {
                        Log.e("a", response);
                    }
                })


                .request();


    }
    public void delete( Map<String,String> header){
            String url = "http://www.yeens.xyz/api.php/v1.user/ulog?type=2&ids=183901";
       BPRequest.getInstance()
                    .setMethod(BPRequest.Method.DELETE)
                    .setUrl(url)
//                .setParams(param)
                    .setRequestTag(mRequestTag)
                    .setHeader(header)
                   .setOnResponseString(new BPListener.OnResponseString() {
                       @Override
                       public void onResponse(String response) {
                            Log.e("asdf",response);
                       }
                   })
                .setOnException(new BPListener.OnException() {
                    @Override
                    public void onException(Exception e, int code, String response) {
                        Log.e("a", response);
                    }
                })
            .request();
    }



}