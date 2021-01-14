package com.example.netlibrary.okhttps;

import android.content.Context;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.RequestStrategy;
import com.example.netlibrary.BPListener;

import org.json.JSONObject;

import java.util.Map;

public class OkhttpsStrategy implements RequestStrategy {


    public OkhttpsStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        OkhttpsHelper.getInstance().init(config);
    }

    public String getRequestTag() {
        return null;
    }

    public void setRequestTag(String requestTag) {
    }


    public void cancelRequests(Object tag){
        OkhttpsHelper.getInstance().cancelRequests(tag);
    }


    public <E> void requestGson(int method, final String url, final Map<String,String> header, final Map<String,String> param, Class<E> mClass,
                                boolean needCache,String requestTag, final BPListener.OnResponseListener<E> listener) {
        OkhttpsHelper.getInstance().requestGson(method,url,header,param,mClass,requestTag,needCache,listener);
    }

    public <E> void requestString(int method, final String url, final Map<String,String> header,final Map<String,String> param,
                                  boolean needCache,String requestTag,final BPListener.OnResponseStrListener listener) {
        OkhttpsHelper.getInstance().requestString(method,url,header,param,requestTag,needCache,listener);

    }
    public <E> void requestNesString(int method, final String url, final Map<String,String> header,final Map<String,String> param,
                                  boolean needCache,String requestTag,final BPListener.OnResponseNesStrListener listener) {
        OkhttpsHelper.getInstance().requestNewString(method,url,header,param,requestTag,needCache,listener);

    }

    public <E> void requestJsonRequest(int method, final String url, final Map<String,String> header, final JSONObject param,
                                       boolean needCache,String requestTag, final BPListener.OnResponseStrListener listener) {
        OkhttpsHelper.getInstance().requestJsonRequest(method,url,header,param,requestTag,needCache,listener);

    }
    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String,String> header, final JSONObject param,
                                            boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener) {
        OkhttpsHelper.getInstance().requestJsonArrayRequest(method,url,header,param,requestTag,needCache,listener);

    }
    public <E> void requestForm(int method, final String url, final Map<String,String> header, final Map<String,String> paramJsonObject,
                                boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener) {

    }

}
