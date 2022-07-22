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
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mBaseModel.cancelRequests(mRequestTag);
//            }
//        },300);

//
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
                    public void onResponse(Response response) {
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

    public void getToken() {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", "308678991225145");
        params.put("client_secret", "dafe2bf3d29146ec6d01300e148438ff");
        params.put("grant_type", "authorization_code");
        params.put("redirect_uri", "https://one-piece-luffy.github.io/");
        params.put("code", "AQBXHevB0ZtwtR3vKEUfjya1uNpAeHvkzUif3yLcRoUiXkVIE7s5_9qc18pVP6jdyYFsV0A5eXztJihrcz-llWcffGtZMMKNP74xt2OB2FL4L4l9l26EdrIiEdRIrBn1gKOfj_y8Ppuq3LopoAYU7tEv5DMXFcrU89bLhUnsEZNjl40lTyD_bSm96tmE2UevSCIm9YI-oz-C1LC-c3DwDkr24saWjBXP3b6VCtY2Imexqg");
        String url = "https://api.instagram.com/oauth/access_token";
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.POST)
                .setUrl(url)
                .setParams(params)
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf","res:"+response);
                    }
                })
                .setOnException((e, code, response) -> {
                    e.printStackTrace();
                })
                .request();

    }

    public void requestDailymotion(){
        Map<String, String> header = new HashMap<>();
        header.put("user-agent","Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Mobile Safari/537.36");
        String url = "https://gimy.app/cat/2--------1---.html";

        BPRequest.getInstance()
                .setMethod(BPRequest.Method.GET)
                .setUrl(url)
                .setHeader(header)
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf","res:"+response);
                    }
                })
                .setOnException((e, code, response) -> {
                    e.printStackTrace();
                })
                .request();
    }

}