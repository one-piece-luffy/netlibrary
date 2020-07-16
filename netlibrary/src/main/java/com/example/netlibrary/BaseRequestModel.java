package com.example.netlibrary;

import com.example.netlibrary.volley.VolleyHelper;
import com.example.netlibrary.volley.VolleyListener;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by wanglin on 2017/10/11.
 */

public class BaseRequestModel {
    private static final String TAG = "BaseRequestModel";
    //request tag http
    private String mRequestTag = "requestTag";

    public BaseRequestModel(String requestTag) {
        mRequestTag = requestTag;
    }

    public String getRequestTag() {
        return mRequestTag;
    }

    public void setRequestTag(String requestTag) {
        this.mRequestTag = requestTag;
    }


    public void cancelRequests(Object tag){
        VolleyHelper.getInstance().cancelPendingRequests(tag);
    }
    public void cancelRequests(){
        VolleyHelper.getInstance().cancelPendingRequests(mRequestTag);
    }


    public <E> void requestGson(int method, final String url, final Map<String,String> header,final Map<String,String> param, Class<E> mClass,
             boolean needCache,final VolleyListener.OnResponseListener<E> listener) {
        VolleyHelper.getInstance().requestGson(method,url,header,param,mClass,mRequestTag,needCache,listener);
    }

    public <E> void requestString(int method, final String url, final Map<String,String> header,final Map<String,String> param,
                                  boolean needCache,final VolleyListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestString(method,url,header,param,mRequestTag,needCache,listener);
    }

    public <E> void requestJsonRequest(int method, final String url, final Map<String,String> header, final JSONObject paramJsonObject,
                                       boolean needCache, final VolleyListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestJsonRequest(method,url,header,paramJsonObject,mRequestTag,needCache,listener);
    }
    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String,String> header, final String paramJsonObject,
                                       boolean needCache, final VolleyListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestJsonArrayRequest(method,url,header,paramJsonObject,mRequestTag,needCache,listener);
    }
    public <E> void requestForm(int method, final String url, final Map<String,String> header, final Map<String,String> paramJsonObject,
                                       boolean needCache, final VolleyListener.OnResponseStrListener listener) {
        VolleyHelper.getInstance().requestForm(method,url,header,paramJsonObject,mRequestTag,needCache,listener);
    }

}
