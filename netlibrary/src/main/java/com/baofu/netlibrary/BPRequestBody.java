package com.baofu.netlibrary;

import com.baofu.netlibrary.okhttp.OkhttpStrategy;

import java.util.Map;

public class BPRequestBody<E> {
    public int method;
    public String url;
    public Map<String,String> header;
    public Map<String,String> params;
    public Class<E> clazz;
    public String paramsJson;
    //加密连接
    public boolean encryptionUrl;
    //加密位移
    public int encryptionDiff;
    //加密路径
    public String appenEncryptPath;
    //非加密路径
    public String appenPath;
    public boolean needCache;
    public String requestTag;
    public BPListener.OnResponseBean onResponseBean;
    public BPListener.OnResponseString  onResponseString;
    public BPListener.OnResponse onResponse;
    public BPListener.OnException  onException;
    public BPListener.onCacheBean  onCacheBean;
    public BPListener.onCacheString  onCacheString;

    public static class Builder  {
        BPRequestBody mBPRequestBody;
        public Builder(){
            mBPRequestBody=new BPRequestBody();
        }
       
        public Builder setMethod(int method) {
            mBPRequestBody.method=method;
            return this;
        }

        
        public Builder setUrl(String url) {
            mBPRequestBody.url=url;
            return this;
        }

        
        public Builder setHeader(Map<String, String> header) {
            mBPRequestBody.header=header;
            return this;
        }

        
        public Builder setParams(Map<String, String> params) {
            mBPRequestBody.params=params;
            return this;
        }

        public  Builder setParamsJson(String json){
            mBPRequestBody.paramsJson=json;
            return this;
        }

        
        public Builder setNeedCache(boolean needCache) {
            mBPRequestBody.needCache=needCache;
            return this;
        }

        
        public Builder setRequestTag(String requestTag) {
            mBPRequestBody.requestTag=requestTag;
            return this;
        }

        
        public <E> Builder setClazz(Class<E> clazz) {
            mBPRequestBody.clazz=clazz;
            return this;
        }

        
        public <E> Builder setOnResponseBean(Class<E> clazz,BPListener.OnResponseBean<E> onResponseBean) {
            mBPRequestBody.onResponseBean=onResponseBean;
            mBPRequestBody.clazz=clazz;
            return this;
        }

        
        public Builder setOnResponseString(BPListener.OnResponseString onResponseString) {
            mBPRequestBody.onResponseString=onResponseString;
            return this;
        }
        
        public Builder setOnResponse(BPListener.OnResponse onResponse) {
            mBPRequestBody.onResponse=onResponse;
            return this;
        }

        
        public Builder setOnException(BPListener.OnException onException) {
            mBPRequestBody.onException=onException;
            return this;
        }

        
        public Builder setOnCacheBean(BPListener.onCacheBean onCacheBean) {
            mBPRequestBody.onCacheBean=onCacheBean;
            return this;
        }

        
        public Builder setOnCacheString(BPListener.onCacheString onCacheString) {
            mBPRequestBody.onCacheString=onCacheString;
            return this;
        }
        public Builder encryptionUrl(boolean encryptionUrl) {
            mBPRequestBody.encryptionUrl=encryptionUrl;
            return this;
        }
        public Builder encryptionDiff(int encryptionDiff) {
            mBPRequestBody.encryptionDiff=encryptionDiff;
            return this;
        }
        public Builder appenEncryptPath(String appenEncryptPath) {
            mBPRequestBody.appenEncryptPath=appenEncryptPath;
            return this;
        }
        public Builder appenPath(String appenPath) {
            mBPRequestBody.appenPath=appenPath;
            return this;
        }

        
        public BPRequestBody build() {
            return mBPRequestBody;
        }

        public void request(){
            if(BPRequest.getInstance().mStrategy==null){
                BPRequest.getInstance().mStrategy=new OkhttpStrategy();
            }
            BPRequest.getInstance().mStrategy.request(this.build());
        }
        public <T> T requestSync(){
            if(BPRequest.getInstance().mStrategy==null){
                BPRequest.getInstance().mStrategy=new OkhttpStrategy();
            }
            Object o= BPRequest.getInstance().mStrategy.requestSync(this.build());
            if(o==null)
                return null;
            try {
                return (T) o;
            }catch (Exception e){

            }
           return null;
        }
        public String requestStringSync(){
            if(BPRequest.getInstance().mStrategy==null){
                BPRequest.getInstance().mStrategy=new OkhttpStrategy();
            }
            return BPRequest.getInstance().mStrategy.requestStringSync(this.build());
        }
    }

}
