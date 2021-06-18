package com.example.netlibrary.okhttp;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequestBody;
import com.example.netlibrary.RequestStrategy;

public class OkhttpStrategy implements RequestStrategy {


    public OkhttpStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        OkhttpHelper.getInstance().init(config);
    }


    public void cancelRequests(Object tag) {
        OkhttpHelper.getInstance().cancelRequests(tag);
    }

    @Override
    public void request(BPRequestBody builder) {
        OkhttpHelper.getInstance().request(builder);
    }


}
