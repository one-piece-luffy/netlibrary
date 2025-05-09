package com.baofu.netlibrary;

import com.baofu.netlibrary.okhttp.OkhttpStrategy;

import java.util.Map;

public class BPRequest {
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
    public interface STRATEGY_TYPE {
        int VOLLEY = 1;
        int OKHTTP = 2;
    }


    private static BPRequest mInstance;

    public RequestStrategy mStrategy;

    // 单例模式，节省资源
    public static BPRequest getInstance() {
        if (mInstance == null) {
            synchronized (BPRequest.class) {
                if (mInstance == null) {
                    mInstance = new BPRequest();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    public BPRequest() {
//        mStrategy = new VolleyStrategy();
//        mStrategy = new OkhttpsStrategy();
    }

    public void init(BPConfig config){
        if(config==null){
            mStrategy = new OkhttpStrategy();
        }else {
            switch (config.strategyType){
                case 1:
                    mStrategy = new OkhttpStrategy();
                    break;
                case 2:
                    mStrategy = new OkhttpStrategy();
                    break;
                case 0:
                default:
                    mStrategy = new OkhttpStrategy();
            }
        }
        mStrategy.init(config);

    }

    /**
     * 策略模式的注入操作
     *
     * @param strategy
     */
    public void setStrategy(RequestStrategy strategy) {
        mStrategy = strategy;
    }




    public BPRequestBody.Builder setMethod(int method) {
        return new BPRequestBody.Builder().setMethod(method);
    }

    public BPRequestBody.Builder setUrl(String url) {
        return new BPRequestBody.Builder().setUrl(url);
    }

    public BPRequestBody.Builder setHeader(Map<String, String> header) {
        return new BPRequestBody.Builder().setHeader(header);
    }
    public BPRequestBody.Builder setParams(Map<String, String> params) {
        return new BPRequestBody.Builder().setParams(params);
    }
    public BPRequestBody.Builder setParamsJson(String json){
        return new BPRequestBody.Builder().setParamsJson(json);
    }
    public BPRequestBody.Builder setNeedCache(boolean needCache) {
        return new BPRequestBody.Builder().setNeedCache(needCache);
    }
    public BPRequestBody.Builder setRequestTag(String requestTag) {
        return new BPRequestBody.Builder().setRequestTag(requestTag);
    }
    public <E> BPRequestBody.Builder setClazz(Class<E> clazz) {
        return new BPRequestBody.Builder().setClazz(clazz);
    }
    public <E> BPRequestBody.Builder setOnResponseBean(Class<E> clazz, BPListener.OnResponseBean<E> onResponseBean) {
        return new BPRequestBody.Builder().setOnResponseBean(clazz,onResponseBean);
    }
    public BPRequestBody.Builder setOnResponseString(BPListener.OnResponseString onResponseString) {
        return new BPRequestBody.Builder().setOnResponseString(onResponseString);
    }
    public BPRequestBody.Builder setOnResponse(BPListener.OnResponse onResponse) {
        return new BPRequestBody.Builder().setOnResponse(onResponse);
    }
    public BPRequestBody.Builder setOnException(BPListener.OnException onException) {
        return new BPRequestBody.Builder().setOnException(onException);
    }
    public BPRequestBody.Builder setOnCacheBean(BPListener.onCacheBean onCacheBean) {
        return new BPRequestBody.Builder().setOnCacheBean(onCacheBean);
    }

    public BPRequestBody.Builder setOnCacheString(BPListener.onCacheString onCacheString) {
        return new BPRequestBody.Builder().setOnCacheString(onCacheString);
    }
    public BPRequestBody.Builder appenPath(String appenPath) {
        return new BPRequestBody.Builder().appenPath(appenPath);
    }

}
