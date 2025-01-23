package com.baofu.netlib;

import android.util.Base64;
import android.util.Log;

import com.baofu.netlibrary.utils.NetUtils;

public class NetEncodeUtil {
    public static void main(String param[]){
        String url = Base64.encodeToString("http://duanjudaquan2.fun:9898".getBytes(), Base64.DEFAULT);
        String a=new String( NetUtils.encode(url.toCharArray(),3));
//        a= "]Z4l]ZTy\\5IzgJoyepYn";
        System.out.println("encode:"+a);
        System.out.println("dencode:"+NetUtils.decodePassword(a,-3));
    }
}
