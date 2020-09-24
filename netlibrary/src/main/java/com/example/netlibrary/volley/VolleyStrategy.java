package com.example.netlibrary.volley;

import android.content.Context;
import android.util.Log;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.RequestStrategy;
import com.example.netlibrary.BPListener;

import org.json.JSONObject;

import java.util.Map;

public class VolleyStrategy implements RequestStrategy {


    public VolleyStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        VolleyHelper.getInstance().init(config);
    }

    public String getRequestTag() {
        return null;
    }

    public void setRequestTag(String requestTag) {
    }


    public void cancelRequests(Object tag){
        VolleyHelper.getInstance().cancelPendingRequests(tag);
    }



    public <E> void requestGson(int method, final String url, final Map<String,String> header, final Map<String,String> param, Class<E> mClass,
                                boolean needCache, String requestTag,final BPListener.OnResponseListener<E> listener) {
        VolleyHelper.getInstance().requestGson(method,url,header,param,mClass,requestTag,needCache,listener);
    }

    public <E> void requestString(int method, final String url, final Map<String,String> header,final Map<String,String> param,
                                  boolean needCache,String requestTag,final BPListener.OnResponseStrListener listener) {
        Log.e("time",System.currentTimeMillis()+"");
        VolleyHelper.getInstance().requestString(method,url,header,param,requestTag,needCache,listener);
    }

    public <E> void requestJsonRequest(int method, final String url, final Map<String,String> header, final JSONObject paramJsonObject,
                                       boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestJsonRequest(method,url,header,paramJsonObject,requestTag,needCache,listener);
    }
    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String,String> header, final JSONObject paramJsonObject,
                                            boolean needCache,String requestTag, final BPListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestJsonArrayRequest(method,url,header,paramJsonObject,requestTag,needCache,listener);
    }
    public <E> void requestForm(int method, final String url, final Map<String,String> header, final Map<String,String> paramJsonObject,
                                boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestForm(method,url,header,paramJsonObject,requestTag,needCache,listener);
    }

}
