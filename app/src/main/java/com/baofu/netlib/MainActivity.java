package com.baofu.netlib;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.Application;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.netlibrary.BaseViewModel;
import com.example.netlibrary.volley.VolleyHelper;
import com.example.netlibrary.volley.VolleyListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 请求方式1
         */
        VolleyHelper.getInstance().requestString(0, "https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website", null, null, System.currentTimeMillis() + "", false, new VolleyListener.OnResponseStrListener() {
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


        /**
         * 请求方式2
         */
//        MonitorViewModel viewModel= ViewModelProviders.of(this).get(MonitorViewModel.class);
////        MonitorViewModel viewModel= new MonitorViewModel(getApplication());
//        viewModel.request();

    }

}