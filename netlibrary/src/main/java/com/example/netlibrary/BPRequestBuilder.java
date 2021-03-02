package com.example.netlibrary;

import java.util.Map;

public abstract class BPRequestBuilder {


    public abstract BPRequestBuilder setMethod(int method);


    public abstract BPRequestBuilder setUrl(String url);


    public abstract BPRequestBuilder setHeader(Map<String, String> header);


    public abstract BPRequestBuilder setParams(Map<String, String> params);
    public abstract BPRequestBuilder setParamsJson(String json);

    public abstract BPRequestBuilder setNeedCache(boolean needCache);

    public abstract BPRequestBuilder setRequestTag(String requestTag);


    public abstract <E> BPRequestBuilder setClazz(Class<E> clazz);


    public  abstract <E> BPRequestBuilder setOnResponseBean(Class<E> clazz,BPListener.OnResponseBean<E> onResponseBean);


    public abstract BPRequestBuilder setOnResponseString(BPListener.OnResponseString onResponseString);

    public abstract BPRequestBuilder setOnResponse(BPListener.OnResponse onResponse);


    public abstract BPRequestBuilder setOnException(BPListener.OnException onException);


    public abstract BPRequestBuilder setOnCacheBean(BPListener.onCacheBean onCacheBean);


    public  abstract BPRequestBuilder setOnCacheString(BPListener.onCacheString onCacheString);

    public abstract BPRequestBody build();
}
