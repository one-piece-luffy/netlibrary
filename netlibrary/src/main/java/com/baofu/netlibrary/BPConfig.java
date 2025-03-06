package com.baofu.netlibrary;

import android.content.Context;

import com.baofu.netlibrary.listener.ResponseInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Interceptor;


public class BPConfig {

    public Context context;
    public int strategyType;
    public Map<String, String> header;// 通用header，所有请求都会加
    public List<Interceptor> interceptorList;
    public ResponseInterceptor responseInterceptor;
    //禁止使用代理
    public boolean banProxy;
    public int timeout;

    private BPConfig(Builder builder) {
        this.context = builder.context;
        this.strategyType = builder.strategyType;
        this.header = builder.header;
        this.interceptorList = builder.interceptorList;
        this.responseInterceptor = builder.responseInterceptor;
        this.banProxy=builder.banProxy;
        this.timeout=builder.timeout;
    }

    public static class Builder {
        private Context context;
        private int strategyType;
        private Map<String, String> header;
        private List<Interceptor> interceptorList;
        private ResponseInterceptor responseInterceptor;
        private boolean banProxy;
        private int timeout;

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

        public Builder addResponseInterceptor(ResponseInterceptor listener) {
            this.responseInterceptor = listener;
            return this;
        }


        public BPConfig build() {
            return new BPConfig(this);
        }

    }


}
