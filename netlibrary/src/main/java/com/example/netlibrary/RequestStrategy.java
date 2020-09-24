package com.example.netlibrary;

import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

public interface RequestStrategy {

    public void init(BPConfig config);

    public String getRequestTag();

    public void setRequestTag(String requestTag);


    public void cancelRequests(Object tag);

    public <E> void requestGson(int method, final String url, final Map<String,String> header, final Map<String,String> param, Class<E> mClass,
                                boolean needCache, String requestTag,final BPListener.OnResponseListener<E> listener);

    public <E> void requestString(int method, final String url, final Map<String,String> header,final Map<String,String> param,
                                  boolean needCache,String requestTag,final BPListener.OnResponseStrListener listener);

    public <E> void requestJsonRequest(int method, final String url, final Map<String,String> header, final JSONObject paramJsonObject,
                                       boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener) ;
    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String,String> header, final JSONObject paramJsonObject,
                                            boolean needCache, String requestTag,final BPListener.OnResponseStrListener listener);
    public <E> void requestForm(int method, final String url, final Map<String,String> header, final Map<String,String> paramJsonObject,
                                boolean needCache,String requestTag, final BPListener.OnResponseStrListener listener) ;
}
