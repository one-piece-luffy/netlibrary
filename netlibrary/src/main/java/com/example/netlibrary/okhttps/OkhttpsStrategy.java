package com.example.netlibrary.okhttps;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequestBody;
import com.example.netlibrary.RequestStrategy;

public class OkhttpsStrategy implements RequestStrategy {


    public OkhttpsStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        OkhttpsHelper.getInstance().init(config);
    }



    public void cancelRequests(Object tag){
        OkhttpsHelper.getInstance().cancelRequests(tag);
    }

    @Override
    public void request(BPRequestBody builder) {
        OkhttpsHelper.getInstance().request(builder);
    }



}
