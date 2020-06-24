package com.baofu.netlib;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.VolleyError;
import com.example.netlibrary.BaseViewModel;
import com.example.netlibrary.volley.VolleyListener;

public class MonitorViewModel extends BaseViewModel {


    public MonitorViewModel(@NonNull Application application) {
        super(application);
    }


    public void request() {
        mBaseModel.requestString(0, "https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website", null, null, false, new VolleyListener.OnResponseStrListener() {
            @Override
            public void onResponse(String response) {
                Log.e("a", response);
            }

            @Override
            public void onCache(String response) {

            }

            @Override
            public void onNotModify() {

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}