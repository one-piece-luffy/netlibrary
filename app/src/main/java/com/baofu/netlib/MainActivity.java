package com.baofu.netlib;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.baofu.netlib.bean.ConfigModelBean;
import com.baofu.netlibrary.BPListener;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.utils.NetUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    MonitorViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);

        viewModel = ViewModelProviders.of(this).get(MonitorViewModel.class);
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 请求方式2
                 */

                viewModel.request();
            }
        });


//        viewModel.request();//异步请求
//        viewModel.requestStringSync();//同步请求，返回string
//        viewModel.requestSync();//同步请求，返回bean

//        viewModel.getToken();
//        viewModel.requestDailymotion();


//        viewModel.requestcookie();

//        viewModel.requestGson();
//        viewModel.requestCheap(null, null, null, null, null);
//        viewModel.requestSp(null,null,"002142");
//        viewModel.requestStringPost();

//        viewModel.requestGsonPOST();
//        viewModel.sendVifyCode("18259462251");

        String url = Base64.encodeToString("/video/types".getBytes(), Base64.DEFAULT);
        String a=new String( NetUtils.encode(url.toCharArray(),3));
//        a= "]Z4l]ZTy\\5IzgJoyepYn";
        Log.e("asdf","encode:"+a);
        Log.e("asdf","dencode:"+NetUtils.decodePassword(a,-3));
    }

}