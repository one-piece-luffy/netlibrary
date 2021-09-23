package com.example.netlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;

public class NetSharePreference {


    public static void saveCacheByString(Context context,String url,String value) {
        if(context==null){
            return;
        }
        SharedPreferences mSharedPreferences = context.getSharedPreferences("net", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(url, value);
        editor.commit();
    }

    public static String getCacheByString(Context context,String url) {
        if(context==null){
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences("net", Context.MODE_PRIVATE);
        String result = sp.getString(url, null);
        return result;
    }

    public static <E> void saveCache(Context context,String url,E e) {
        if(context==null){
            return;
        }
        SharedPreferences mSharedPreferences = context.getSharedPreferences("net", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(url, JSON.toJSONString(e));
        editor.commit();
    }

    public static <E> E  getCache(Context context,String url, Class<E> cls) {
        if(context==null){
            return null;
        }
        SharedPreferences sp = context.getSharedPreferences("net", Context.MODE_PRIVATE);
        String json = sp.getString(url,null);
        if (TextUtils.isEmpty(json))
            return null;
        try {
            return JSON.parseObject(json, cls);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



}
