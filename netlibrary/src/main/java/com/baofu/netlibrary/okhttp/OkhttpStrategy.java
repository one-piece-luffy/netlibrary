package com.baofu.netlibrary.okhttp;

import static com.baofu.netlibrary.okhttp.OkhttpHelper.UNKNOW;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.baofu.netlibrary.BPConfig;
import com.baofu.netlibrary.BPRequestBody;
import com.baofu.netlibrary.RequestStrategy;
import com.baofu.netlibrary.utils.NetUtils;

import okhttp3.Response;

public class OkhttpStrategy implements RequestStrategy {

    Handler mMainHandler = new Handler(Looper.getMainLooper());
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
            handlerError(builder, new Exception("url 不能为空"),UNKNOW);
            return;
        }
        if (builder.encryptionUrl) {
            try {
                String url = NetUtils.decodePassword(builder.url, builder.encryptionDiff);
                builder.url = url;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, e, UNKNOW);
                return;
            }
        }
        if (!TextUtils.isEmpty(builder.appenPath)) {
            builder.url += builder.appenPath;
        }

        if( builder.url.startsWith("http://") || builder.url.startsWith("https://")){
            OkhttpHelper.getInstance().request(builder);

        }else {
             handlerError(builder, new Exception("url必须是http或者https开头"),UNKNOW);
            Log.e("OkhttpStrategy", "==============url必须是http或者https开头==============");
        }

    }

    @Override
    public Response requestSync(BPRequestBody builder) {
        if(builder==null|| TextUtils.isEmpty(builder.url)){
            Log.e("OkhttpStrategy","==============url 不能为空==============");
            handlerError(builder, new Exception("url 不能为空"),UNKNOW);
            return null;
        }
        if (builder.encryptionUrl) {
            try {
                String url = NetUtils.decodePassword(builder.url, builder.encryptionDiff);
                builder.url = url;
            } catch (Exception e) {
                e.printStackTrace();
                handlerError(builder, e, UNKNOW);
                return null;
            }
        }
        if (!TextUtils.isEmpty(builder.appenPath)) {
            builder.url += builder.appenPath;
        }
        if( builder.url.startsWith("http://") || builder.url.startsWith("https://")){
            return OkhttpHelper.getInstance().requestSync(builder);

        }else {
            handlerError(builder, new Exception("url必须是http或者https开头"),UNKNOW);
            Log.e("OkhttpStrategy", "==============url必须是http或者https开头==============");
        }
        return null;
    }


    private <E> void handlerError(BPRequestBody<E> builder, Exception e, int code) {
        if (builder.onException != null) {
            mMainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Exception exception=e;
                    if(exception==null){
                        exception=new Exception("UNKNOW");
                    }
                    builder.onException.onException(exception, code, null);
                }
            });


        }
    }

}
