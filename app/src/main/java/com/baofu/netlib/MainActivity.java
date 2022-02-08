package com.baofu.netlib;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.baofu.netlib.bean.ConfigModelBean;
import com.baofu.netlibrary.BPListener;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.utils.NetUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);


        /**
         * 请求方式2
         */
        MonitorViewModel viewModel = ViewModelProviders.of(this).get(MonitorViewModel.class);

        viewModel.request();//异步请求
//        viewModel.requestStringSync();//同步请求，返回string
        viewModel.requestSync();//同步请求，返回bean

//        viewModel.requestcookie();

//        viewModel.requestGson();
//        viewModel.requestCheap(null, null, null, null, null);
//        viewModel.requestSp(null,null,"002142");
//        viewModel.requestStringPost();

//        viewModel.requestGsonPOST();
//        viewModel.sendVifyCode("18259462251");
        String a=new String( NetUtils.encode("aHR0cHM6Ly9pZ3Muc2YtY29udmVydGVyLmNvbS9hcGkvc3Rvcmllcy8=".toCharArray(),3));
        Log.e("asdf","encode:"+a);
        Log.e("asdf","dencode:"+NetUtils.decodePassword(a,-3));
    }

}