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


                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
                    @Override
                    public void onResponse(ConfigModelBean response) {
                        Log.e("time", response.toString() + "");
                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
                    }
                })
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
        String url = "https://vt.tiktok.com/ZSMSWsfc7/";
        Map<String, String> header = new HashMap<>();
        header.put("source", "dev");
        header.put("User-Agent"
                , "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.5845.97 Safari/537.36 Core/1.116.485.400 QQBrowser/13.6.6321.400");
        BPRequest.getInstance()
                .setMethod(BPRequest.Method.GET)
                .setHeader(header)
                .setUrl(url)
                .setOnResponseString(new BPListener.OnResponseString() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("asdf", response);
                        try {
                            String start = "\",\"https:{1}";
//                            String end = "], \\[\"NavigationMetrics";
                            String end = "\"\\]{1}";
                            String filter = start + ".*" + "is_play{1}" + ".*" + end;
                            String regex = "https?:[\\s\\S]{12}[^\"]*is_play_url=1[^\"]*";
//                            String urlPattern = "https?[\\s\\S]{12}(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";

                            Pattern pattern = Pattern.compile(regex);
                            Matcher matcher = pattern.matcher(response);
                            while (matcher.find()) {

                                String result = matcher.group().replace("\\u002F", "/");
                                int starIndex = matcher.start();
                                Log.e("asdf", result);
                                break;
                            }
                            // 定义正则表达式来匹配 JSON 字符串，JSON 以 { 开始，以 } 结束
                             regex = "\\{.*\\}";
                             pattern = Pattern.compile(regex);
                             matcher = pattern.matcher(response);
                            if (matcher.find()) {
                                String jsonString = matcher.group();

                                Pattern subPatern = Pattern.compile("\"originCover\":\\s*\"([^\"]+)\"");
                                Matcher subMatcher = subPatern.matcher(jsonString);
                                if (subMatcher.find()){
                                    String cover=subMatcher.group().replace("\\u002F", "/");
                                    int index=cover.indexOf("http");
                                    cover=cover.substring(index,cover.length()-1);
                                    Log.e("asdf","originCover: "+cover);
                                }

                                subPatern = Pattern.compile("\"avatarThumb\":\\s*\"([^\"]+)\"");
                                subMatcher = subPatern.matcher(jsonString);
                                if (subMatcher.find()){
                                    String avatarThumb=subMatcher.group().replace("\\u002F", "/");
                                    int index=avatarThumb.indexOf("http");
                                    avatarThumb=avatarThumb.substring(index,avatarThumb.length()-1);
                                    Log.e("asdf","avatarThumb: "+avatarThumb);
                                }

                                subPatern = Pattern.compile("\"desc\":\\s*\"([^\"]+)\"");
                                subMatcher= subPatern.matcher(jsonString);
                                if (subMatcher.find()){
                                    String desc=subMatcher.group();
                                    desc=desc.substring("\"desc\":\"".length(),desc.length()-1);
                                    Log.e("asdf","desc: "+desc);
                                }
                                subPatern = Pattern.compile("\"nickname\":\\s*\"([^\"]+)\"");
                                subMatcher= subPatern.matcher(jsonString);
                                if (subMatcher.find()){
                                    String nickname=subMatcher.group();
                                    nickname=nickname.substring("\"nickname\":\"".length(),nickname.length()-1);
                                    Log.e("asdf","nickname: "+nickname);
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                            requestPlanB(insUrl);
                        }
                        Toast.makeText(BaseApplication.getInstance(), response, Toast.LENGTH_SHORT).show();
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