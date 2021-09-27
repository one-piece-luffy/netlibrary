package com.example.netlibrary;

import com.example.netlibrary.okhttp.OkhttpStrategy;

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


}
