package com.baofu.netlibrary;

import android.content.Context;

import com.baofu.netlibrary.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;


public class BPConfig {

    public Context context;
    public int strategyType;
    public Map<String, String> header;// 通用header，所有请求都会加
    public List<Interceptor> interceptorList;
    public RequestListener onResponseListener;
    //禁止使用代理
    public boolean banProxy;
    public int timeout;
    //加密连接
    public boolean encryptionUrl;
    //加密位移
    public int encryptionDiff;

    private BPConfig(Builder builder) {
        this.context = builder.context;
        this.strategyType = builder.strategyType;
        this.header = builder.header;
        this.interceptorList = builder.interceptorList;
        this.onResponseListener = builder.onResponseListener;
        this.banProxy=builder.banProxy;
        this.timeout=builder.timeout;
        this.encryptionUrl = builder.encryptionUrl;
        this.encryptionDiff = builder.encryptionDiff;
    }

    public static class Builder {
        private Context context;
        private int strategyType;
        private Map<String, String> header;
        private List<Interceptor> interceptorList;
        private RequestListener onResponseListener;
        private boolean banProxy;
        private int timeout;
        //加密连接
        private boolean encryptionUrl;
        //加密位移
        private int encryptionDiff;

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder strategyType(int strategyType) {
            this.strategyType = strategyType;
            return this;
        }
        public Builder banProxy(boolean banProxy) {
            this.banProxy = banProxy;
            return this;
        }
        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }
        public Builder encryptionUrl(boolean encryptionUrl) {
            this.encryptionUrl = encryptionUrl;
            return this;
        }
        public Builder encryptionDiff(int encryptionDiff) {
            this.encryptionDiff = encryptionDiff;
            return this;
        }

        public Builder addHeader(Map<String, String> header) {
            this.header = header;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptorList == null) {
                interceptorList = new ArrayList();
            }
            interceptorList.add(interceptor);
            return this;
        }

        //volley还没处理
        public Builder setRequestListener(RequestListener listener) {
            this.onResponseListener = listener;
            return this;
        }


        public BPConfig build() {
            return new BPConfig(this);
        }

    }


}
