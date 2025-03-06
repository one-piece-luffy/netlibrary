package com.baofu.netlib;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequest;
import com.baofu.netlibrary.listener.ResponseInterceptor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class BaseApplication extends Application{
    public static BaseApplication instance;
    public static BaseApplication getInstance(){
        if(instance==null){
            instance=new BaseApplication();
        }
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        instance=this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Map<String, String> header = new ConcurrentHashMap<>();
        header.put("User-Agent", "UA");
        ResponseInterceptor responseInterceptor=new ResponseInterceptor() {
            @Override
            public Response responseListener(Headers headers, int status, String url, Response originalResponse) {
                //这里是子线程
                Log.e("asdf","所有的请求");
                try {
                    ResponseBody responseBody = originalResponse.body();
                    if (responseBody != null) {
                        // 将ResponseBody转换为String，以便修改
                        String originalString = responseBody.string();;
                        // 在这里修改字符串内容
                        String modifiedString = "惊不惊喜!";

                        // 创建新的ResponseBody
                        ResponseBody modifiedBody = ResponseBody.create(modifiedString, responseBody.contentType());
                        // 可以使用新的ResponseBody做一些操作，例如再次封装成Response等
                        Response newResponse=originalResponse.newBuilder()
                                .body(modifiedBody)
                                .build();
                        return newResponse;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return originalResponse;
            }

            @Override
            public Exception exceptionListener(String url, String error, Exception e, int code) {
                //这里是子线程

                //拦截错误，不再向下传递，在这里统一处理
//                        return NetConstans.Interceptor;
                //返回null表示不拦截
                e=new Exception("意不意外");
                return e;
            }
        };
        BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTP).addHeader(header)
                .addInterceptor(new MyTestInterceptor())
                //添加返回结果拦截器
//                .addResponseInterceptor(responseInterceptor)
                .build();
        BPRequest.getInstance().init(config);
    }
}