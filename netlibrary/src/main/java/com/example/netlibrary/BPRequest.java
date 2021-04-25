package com.example.netlibrary;

import android.content.Context;

import com.android.volley.toolbox.ImageLoader;
import com.example.netlibrary.okhttps.OkhttpsStrategy;
import com.example.netlibrary.volley.VolleyStrategy;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class BPRequest {
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }
    public interface STRATEGY_TYPE {
        int OKHTTPS = 0;
        int VOLLEY = 1;
    }


    private static BPRequest mInstance;

    public RequestStrategy mStrategy;

    // 单例模式，节省资源
    public static BPRequest getInstance() {
        if (mInstance == null) {
            synchronized (BPRequest.class) {
                if (mInstance == null) {
                    mInstance = new BPRequest();
                    return mInstance;
                }
            }
        }
        return mInstance;
    }


    public BPRequest() {
//        mStrategy = new VolleyStrategy();
//        mStrategy = new OkhttpsStrategy();
    }

    public void init(BPConfig config){
        if(config==null){
            mStrategy = new OkhttpsStrategy();
        }else {
            switch (config.strategyType){
                case 1:
                    mStrategy = new VolleyStrategy();
                    break;
                case 0:
                default:
                    mStrategy = new OkhttpsStrategy();
            }
        }
        mStrategy.init(config);

    }

    /**
     * 策略模式的注入操作
     *
     * @param strategy
     */
    public void setStrategy(RequestStrategy strategy) {
        mStrategy = strategy;
    }

    X509TrustManager myTrustManager = new X509TrustManager() {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    };

    HostnameVerifier myHostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    SSLContext sslCtx;

    SSLSocketFactory mySSLSocketFactory ;

    public  X509TrustManager getTrustManager(){
        return myTrustManager;
    }
    public HostnameVerifier getHostnameVerifier(){
        return myHostnameVerifier;
    }
    public SSLSocketFactory getSSLSocketFactory(){
        if(sslCtx==null){
            try {
                sslCtx = SSLContext.getInstance("TLS");
                sslCtx.init(null, new TrustManager[] { myTrustManager }, new SecureRandom());
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        if(mySSLSocketFactory==null){
            mySSLSocketFactory=sslCtx.getSocketFactory();
        }


        return mySSLSocketFactory;
    }
}
