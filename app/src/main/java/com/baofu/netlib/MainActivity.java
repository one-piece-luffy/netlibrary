package com.baofu.netlib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        /**
//         * 请求方式1
//         */
//        VolleyHelper.getInstance().requestString(0, "https://api.dongqiudi.com/app/global/2/android.json?mark=gif&platform=android&version=216&android-channel=website", null, null, System.currentTimeMillis() + "", false, new VolleyListener.OnResponseStrListener() {
//            @Override
//            public void onResponse(String response) {
//                Log.e("a", response);
//                Log.e("time",System.currentTimeMillis()+"");
//            }
//
//            @Override
//            public void onCache(String response) {
//
//            }
//
//            @Override
//            public void onNotModify() {
//
//            }
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });


        /**
         * 请求方式2
         */
        MonitorViewModel viewModel = ViewModelProviders.of(this).get(MonitorViewModel.class);
        viewModel.request();

        Handler handler = new Handler();
//        viewModel.requestGson();
//        viewModel.requestCheap(null, null, null, null, null);
//        viewModel.requestSp(null,null,"002142");
        viewModel.requestStringPost();

//        viewModel.requestGsonPOST();
    }

}