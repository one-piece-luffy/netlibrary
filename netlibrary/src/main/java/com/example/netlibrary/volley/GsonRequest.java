package com.example.netlibrary.volley;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;

public class GsonRequest<T> extends Request<T> {

    private Class<T> clazz;

    private Response.Listener<T> mListener;


    public GsonRequest(int method,String url, Class<T> clazz, Response.Listener<T> mListener, Response.ErrorListener listener) {

        super(method, url, listener);

        this.clazz = clazz;

        this.mListener = mListener;

    }

//仿照StringRespnose

    @Override

    protected Response<T> parseNetworkResponse(NetworkResponse response) {

        String parsed;

        try {

            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

        } catch (UnsupportedEncodingException e) {

            parsed = new String(response.data);

        }

//将json字符串解析为javabean

        T t = JSON.parseObject(parsed,clazz);

        return Response.success(t, HttpHeaderParser.parseCacheHeaders(response));

    }

    @Override

    protected void deliverResponse(T response) {

        mListener.onResponse(response);

    }

}
