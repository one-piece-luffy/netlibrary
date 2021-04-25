package com.example.netlibrary;

import android.content.Context;

import java.util.Map;


public class BPConfig {

    public  Context context;
    public  int strategyType;
    public Map<String,String> header;// 通用header，所有请求都会加

    private BPConfig(Builder builder) {
        this.context = builder.context;
        this.strategyType = builder.strategyType;
        this.header = builder.header;
    }

    public static class Builder {
        private  Context context;
        private  int strategyType;
        private Map<String,String> header;

        public Builder context(Context context) {
            this.context= context;
            return this;
        }
        public Builder strategyType(int strategyType) {
            this.strategyType = strategyType;
            return this;
        }
        public Builder addHeader(Map<String,String> header) {
            this.header = header;
            return this;
        }


        public BPConfig build() {
            return new BPConfig(this);
        }
    }
}
