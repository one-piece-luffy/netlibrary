package com.example.netlibrary;

import com.example.netlibrary.BPListener;
import com.example.netlibrary.BPRequestBuilder;
import com.example.netlibrary.BPRequestBody;

import java.util.Map;

public class BPRequestPattern extends BPRequestBuilder {
    BPRequestBody mBPRequestBody;
    public BPRequestPattern(){
        mBPRequestBody=new BPRequestBody();
    }
    @Override
    public BPRequestBuilder setMethod(int method) {
        mBPRequestBody.method=method;
        return this;
    }

    @Override
    public BPRequestBuilder setUrl(String url) {
        mBPRequestBody.url=url;
        return this;
    }

    @Override
    public BPRequestBuilder setHeader(Map<String, String> header) {
        mBPRequestBody.header=header;
        return this;
    }

    @Override
    public BPRequestBuilder setParams(Map<String, String> params) {
        mBPRequestBody.params=params;
        return this;
    }

    public  BPRequestBuilder setParamsJson(String json){
        mBPRequestBody.paramsJson=json;
        return this;
    }

    @Override
    public BPRequestBuilder setNeedCache(boolean needCache) {
        mBPRequestBody.needCache=needCache;
        return this;
    }

    @Override
    public BPRequestBuilder setRequestTag(String requestTag) {
        mBPRequestBody.requestTag=requestTag;
        return this;
    }

    @Override
    public <E> BPRequestBuilder setClazz(Class<E> clazz) {
        mBPRequestBody.clazz=clazz;
        return this;
    }

    @Override
    public <E> BPRequestBuilder setOnResponseBean(Class<E> clazz,BPListener.OnResponseBean<E> onResponseBean) {
        mBPRequestBody.onResponseBean=onResponseBean;
        mBPRequestBody.clazz=clazz;
        return this;
    }

    @Override
    public BPRequestBuilder setOnResponseString(BPListener.OnResponseString onResponseString) {
        mBPRequestBody.onResponseString=onResponseString;
        return this;
    }
    @Override
    public BPRequestBuilder setOnResponse(BPListener.OnResponse onResponse) {
        mBPRequestBody.onResponse=onResponse;
        return this;
    }

    @Override
    public BPRequestBuilder setOnException(BPListener.OnException onException) {
        mBPRequestBody.onException=onException;
        return this;
    }

    @Override
    public BPRequestBuilder setOnCacheBean(BPListener.onCacheBean onCacheBean) {
        mBPRequestBody.onCacheBean=onCacheBean;
        return this;
    }

    @Override
    public BPRequestBuilder setOnCacheString(BPListener.onCacheString onCacheString) {
        mBPRequestBody.onCacheString=onCacheString;
        return this;
    }

    @Override
    public BPRequestBody build() {
        return mBPRequestBody;
    }
}
