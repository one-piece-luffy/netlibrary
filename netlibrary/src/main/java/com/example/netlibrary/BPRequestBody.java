package com.example.netlibrary;

import androidx.annotation.NonNull;

import java.lang.reflect.AnnotatedElement;
import java.util.Map;

public class BPRequestBody<E> {
    public int method;
    public String url;
    public Map<String,String> header;
    public Map<String,String> params;
    public Class<E> clazz;
    public String paramsJson;

    public boolean needCache;
    public String requestTag;
    public BPListener.OnResponseBean onResponseBean;
    public BPListener.OnResponseString  onResponseString;
    public BPListener.OnResponse onResponse;
    public BPListener.OnException  onException;
    public BPListener.onCacheBean  onCacheBean;
    public BPListener.onCacheString  onCacheString;


}
