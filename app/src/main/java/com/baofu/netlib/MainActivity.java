package com.baofu.netlib;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.example.netlibrary.volley.VolleyHelper;
import com.example.netlibrary.volley.VolleyListener;

import java.lang.reflect.Method;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        VolleyHelper.getInstance().requestString(0, "https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website",new HashMap<String, String>(),new HashMap<String, String>(), new VolleyListener.OnResponseStrListener() {
            @Override
            public void onResponse(String response) {
                Log.e("a",response);
            }

            @Override
            public void onCache(String response) {

            }

            @Override
            public void onNotModify() {

            }

            @Override
            public void onErrorResponse(com.android.volley.VolleyError error) {

            }
        } ,System.currentTimeMillis() + "", false);
    }
}