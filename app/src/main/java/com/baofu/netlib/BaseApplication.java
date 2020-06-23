package com.baofu.netlib;

import android.app.Application;

import com.example.netlibrary.volley.VolleyHelper;

public class BaseApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyHelper.getInstance().init(this);
    }
}