package com.example.netlibrary.volley;

import android.content.Context;
import android.util.Log;

import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPRequestBody;
import com.example.netlibrary.RequestStrategy;
import com.example.netlibrary.BPListener;

import org.json.JSONObject;

import java.util.Map;

public class VolleyStrategy implements RequestStrategy {


    public VolleyStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        VolleyHelper.getInstance().init(config);
    }


    public void cancelRequests(Object tag){
        VolleyHelper.getInstance().cancelPendingRequests(tag);
    }

    @Override
    public void request(BPRequestBody builder) {
        VolleyHelper.getInstance().request(builder);
    }


}
