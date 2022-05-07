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
     * 同步请求返回String
     */
    public void requestStringSync() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<String, String> param = new HashMap<>();
                param.put("code", "18933333333");
                param.put("password", "123456");
                Map<String, String> header = new HashMap<>();
                header.put("header", "header");
                String result= BPRequest.getInstance()
                        .setMethod(BPRequest.Method.GET)
                        .setUrl("https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website")
                        .setParams(param)
                        .setRequestTag(System.currentTimeMillis()+"")
                        .setHeader(header)
                        .setNeedCache(true)
                        .setClazz(String.class)

//                .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {
//                    @Override
//                    public void onResponse(ConfigModelBean response) {
//                        Log.e("time",response.toString()+"");
//                        Toast.makeText(BaseApplication.getInstance(), response.getUpdated_at(), Toast.LENGTH_SHORT).show();
//                    }
//                })
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
                            }

                        })
                        .requestStringSync();
                Log.e("asdf","sync result:"+result);
            }
        }).start();

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
                ConfigModelBean result= BPRequest.getInstance()
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
                            }

                        })
                        .requestSync();
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
        header.put("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhaWQiOiJmMWEzNjJkMjg4YzFiOTgwOTljNyIsInJvbCI6ImNhbi1tYW5hZ2UtcGFydG5lcnMtcmVwb3J0cyBjYW4tcmVhZC12aWRlby1zdHJlYW1zIGNhbi1zcG9vZi1jb3VudHJ5IGNhbi1hZG9wdC11c2VycyBjYW4tcmVhZC1jbGFpbS1ydWxlcyBjYW4tbWFuYWdlLWNsYWltLXJ1bGVzIGNhbi1tYW5hZ2UtdXNlci1hbmFseXRpY3MgY2FuLXJlYWQtbXktdmlkZW8tc3RyZWFtcyBjYW4tZG93bmxvYWQtbXktdmlkZW9zIGFjdC1hcyBhbGxzY29wZXMgYWNjb3VudC1jcmVhdG9yIGNhbi1yZWFkLWFwcGxpY2F0aW9ucyIsInNjbyI6InJlYWQgd3JpdGUgZGVsZXRlIGVtYWlsIHVzZXJpbmZvIGZlZWQgbWFuYWdlX3ZpZGVvcyBtYW5hZ2VfY29tbWVudHMgbWFuYWdlX3BsYXlsaXN0cyBtYW5hZ2VfdGlsZXMgbWFuYWdlX3N1YnNjcmlwdGlvbnMgbWFuYWdlX2ZyaWVuZHMgbWFuYWdlX2Zhdm9yaXRlcyBtYW5hZ2VfbGlrZXMgbWFuYWdlX2dyb3VwcyBtYW5hZ2VfcmVjb3JkcyBtYW5hZ2Vfc3VidGl0bGVzIG1hbmFnZV9mZWF0dXJlcyBtYW5hZ2VfaGlzdG9yeSBpZnR0dCByZWFkX2luc2lnaHRzIG1hbmFnZV9jbGFpbV9ydWxlcyBkZWxlZ2F0ZV9hY2NvdW50X21hbmFnZW1lbnQgbWFuYWdlX2FuYWx5dGljcyBtYW5hZ2VfcGxheWVyIG1hbmFnZV9wbGF5ZXJzIG1hbmFnZV91c2VyX3NldHRpbmdzIG1hbmFnZV9jb2xsZWN0aW9ucyBtYW5hZ2VfYXBwX2Nvbm5lY3Rpb25zIG1hbmFnZV9hcHBsaWNhdGlvbnMgbWFuYWdlX2RvbWFpbnMiLCJsdG8iOiJlVEZiVXlaRFFocG5XU1VGR0NvZUFFVWVBUVV0RmpoaFhpMVVNQSIsImFpbiI6MSwiYWRnIjoxLCJpYXQiOjE2NTE4ODE4MjksImV4cCI6MTY1MTkxNzI2MywiZG12IjoiMSIsImF0cCI6ImJyb3dzZXIiLCJhZGEiOiJ3d3cuZGFpbHltb3Rpb24uY29tIiwidmlkIjoiRDlERkYzRjZGRjA0RTQ2Mjk5NDk0QzI3NjE1QkRFNkIiLCJmdHMiOjY4MzYyMCwiY2FkIjoyLCJjeHAiOjIsImNhdSI6Miwia2lkIjoiQUY4NDlERDczQTU4NjNDRDdEOTdEMEJBQjA3MjI0M0IifQ.2HKQm-7Vbez2Tqeea5J75Iv15bSHogLp3s9KDnz3yGE");
        header.put("Origin", "https://www.dailymotion.com");
        header.put("Host", "graphql.api.dailymotion.com");
        String url = "https://graphql.api.dailymotion.com/";
        String json="{\"operationName\":\"WATCHING_VIDEO\",\"variables\":{\"xid\":\"x8alq9b\",\"isSEO\":false},\"query\":\"fragment VIDEO_FRAGMENT on Video {\\n  id\\n  xid\\n  duration\\n  title\\n  description\\n   thumbnailx360: thumbnailURL(size: \\\"x360\\\")\\n  channel {\\n    __typename\\n       organization @skip(if: $isSEO) {\\n      id\\n      xid\\n      owner {\\n        id\\n        xid\\n        __typename\\n      }\\n      __typename\\n    }\\n  }\\n     topics(whitelistedOnly: true, first: 3, page: 1) {\\n    edges {\\n      node {\\n        id\\n        xid\\n        name\\n        names {\\n          edges {\\n            node {\\n              id\\n              name\\n              language {\\n                id\\n                codeAlpha2\\n                __typename\\n              }\\n              __typename\\n            }\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  geoblockedCountries {\\n    id\\n    allowed\\n    denied\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment LIVE_FRAGMENT on Live {\\n  id\\n  xid\\n  startAt\\n  endAt\\n  title\\n  description\\n  thumbnailx60: thumbnailURL(size: \\\"x60\\\")\\n  thumbnailx120: thumbnailURL(size: \\\"x120\\\")\\n  thumbnailx240: thumbnailURL(size: \\\"x240\\\")\\n  thumbnailx360: thumbnailURL(size: \\\"x360\\\")\\n  thumbnailx480: thumbnailURL(size: \\\"x480\\\")\\n  thumbnailx720: thumbnailURL(size: \\\"x720\\\")\\n  thumbnailx1080: thumbnailURL(size: \\\"x1080\\\")\\n  category\\n  createdAt\\n  isInWatchLater\\n  isLiked\\n  isPrivate\\n  isExplicit\\n  isCreatedForKids\\n  bestAvailableQuality\\n  canDisplayAds\\n  embedURL\\n  videoWidth: width\\n  videoHeight: height\\n  stats {\\n    id\\n    views {\\n      id\\n      total\\n      __typename\\n    }\\n    __typename\\n  }\\n  channel {\\n    __typename\\n    id\\n    xid\\n    name\\n    displayName\\n    isArtist\\n    logoURLx25: logoURL(size: \\\"x25\\\")\\n    logoURL(size: \\\"x60\\\")\\n    isFollowed\\n    accountType\\n    coverURLx375: coverURL(size: \\\"x375\\\")\\n    stats {\\n      id\\n      views {\\n        id\\n        total\\n        __typename\\n      }\\n      followers {\\n        id\\n        total\\n        __typename\\n      }\\n      videos {\\n        id\\n        total\\n        __typename\\n      }\\n      __typename\\n    }\\n    country {\\n      id\\n      codeAlpha2\\n      __typename\\n    }\\n    organization @skip(if: $isSEO) {\\n      id\\n      xid\\n      owner {\\n        id\\n        xid\\n        __typename\\n      }\\n      __typename\\n    }\\n  }\\n  language {\\n    id\\n    codeAlpha2\\n    __typename\\n  }\\n  tags {\\n    edges {\\n      node {\\n        id\\n        label\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  moderation {\\n    id\\n    reviewedAt\\n    __typename\\n  }\\n  topics(whitelistedOnly: true, first: 3, page: 1) {\\n    edges {\\n      node {\\n        id\\n        xid\\n        name\\n        names {\\n          edges {\\n            node {\\n              id\\n              name\\n              language {\\n                id\\n                codeAlpha2\\n                __typename\\n              }\\n              __typename\\n            }\\n            __typename\\n          }\\n          __typename\\n        }\\n        __typename\\n      }\\n      __typename\\n    }\\n    __typename\\n  }\\n  geoblockedCountries {\\n    id\\n    allowed\\n    denied\\n    __typename\\n  }\\n  __typename\\n}\\n\\nquery WATCHING_VIDEO($xid: String!, $isSEO: Boolean!) {\\n  video: media(xid: $xid) {\\n    __typename\\n    ... on Video {\\n      id\\n      ...VIDEO_FRAGMENT\\n      __typename\\n    }\\n    ... on Live {\\n      id\\n      ...LIVE_FRAGMENT\\n      __typename\\n    }\\n  }\\n}\\n\"}";

        BPRequest.getInstance()
                .setMethod(BPRequest.Method.POST)
                .setUrl(url)
                .setHeader(header)
                .setParamsJson(json)
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