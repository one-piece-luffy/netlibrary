package com.baofu.netlibrary;



import java.util.Map;

import okhttp3.Response;

/**
 * Created by wanglin on 2017/11/14.
 */

public interface BPListener {



    /**
     *
     * 建造者模式使用==============
     */

    public interface OnResponseBean<E> {

        void onResponse(E response);
    }
    public interface OnResponseString {

        void onResponse(String response);
    }
    public interface OnResponse {

        void onResponse(Response response);
    }
    public interface OnException {

        void onException(Exception e,int code,String response);
    }
    public interface onCacheBean<E> {

        void onCache(E response);
    }
    public interface onCacheString {

        void onCacheString(String response);
    }
}
