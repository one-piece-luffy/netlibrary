package com.example.netlibrary;

import android.content.Context;


public class BPConfig {

    public  Context context;
    public  int strategyType;

    private BPConfig(Builder builder) {
        this.context = builder.context;
        this.strategyType = builder.strategyType;
    }

    public static class Builder {
        private  Context context;
        private  int strategyType;

        public Builder context(Context context) {
            this.context= context;
            return this;
        }
        public Builder strategyType(int strategyType) {
            this.strategyType = strategyType;
            return this;
        }


        public BPConfig build() {
            return new BPConfig(this);
        }
    }
}
