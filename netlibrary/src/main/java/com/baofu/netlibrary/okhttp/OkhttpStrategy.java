package com.baofu.netlibrary.okhttp;

import static com.baofu.netlibrary.okhttp.OkhttpHelper.UNKNOW;

import android.text.TextUtils;
import android.util.Log;

import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.RequestStrategy;

public class OkhttpStrategy implements RequestStrategy {


    public OkhttpStrategy() {
    }

    @Override
    public void init(BPConfig config) {
        OkhttpHelper.getInstance().init(config);
    }


    public void cancelRequests(Object tag) {
        OkhttpHelper.getInstance().cancelRequests(tag);
    }

    @Override
    public void request(BPRequestBody builder) {
        if(builder==null|| TextUtils.isEmpty(builder.url)){
            Log.e("OkhttpStrategy","==============url 不能为空==============");
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            return;
        }
         if( builder.url.startsWith("http://") || builder.url.startsWith("https://")){
            OkhttpHelper.getInstance().request(builder);

        }else {
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            Log.e("OkhttpStrategy", "==============url必须是http或者https开头==============");
        }

    }

    @Override
    public <T> T requestSync(BPRequestBody<T> builder) {
        if(builder==null|| TextUtils.isEmpty(builder.url)){
            Log.e("OkhttpStrategy","==============url 不能为空==============");
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            return null;
        }
        if( builder.url.startsWith("http://") || builder.url.startsWith("https://")){
            return OkhttpHelper.getInstance().requestSync(builder);

        }else {
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            Log.e("OkhttpStrategy", "==============url必须是http或者https开头==============");
        }
        return null;
    }

    @Override
    public String requestStringSync(BPRequestBody builder) {
        if(builder==null|| TextUtils.isEmpty(builder.url)){
            Log.e("OkhttpStrategy","==============url 不能为空==============");
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            return null;
        }
        if( builder.url.startsWith("http://") || builder.url.startsWith("https://")){
            return OkhttpHelper.getInstance().requestStringSync(builder);

        }else {
            if (builder.onException != null ) {
                builder.onException.onException(new Exception("url 不能为空"),UNKNOW,"url 不能为空");

            }
            Log.e("OkhttpStrategy", "==============url必须是http或者https开头==============");
        }
        return null;
    }


}
