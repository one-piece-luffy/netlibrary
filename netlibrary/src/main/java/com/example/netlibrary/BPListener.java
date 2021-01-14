package com.example.netlibrary;


import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import java.util.Map;

/**
 * Created by wanglin on 2017/11/14.
 */

public interface BPListener {

    public interface OnResponseListener<E> {

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(String error);
    }

    public interface OnResponseStrListener {

        void onResponse(String response);

        void onCache(String response);

        void onNotModify();

        void onErrorResponse(String error);
    }

    public interface OnResponseNesStrListener {

        void onResponse(String response, Object obj);

        void onCache(String response);

        void onNotModify();

        void onErrorResponse(String error);
    }

    public interface OnResponseListenerAsyn<E> {

        void doInBackground(NetworkResponse networkResponse, E response);

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(String error);
    }

    public interface OnResponseMultipartListener<E> {
        void onResponse(E response);

        void onErrorResponse(String error);

    }

    public interface OnResponseParseListener<E> {

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(String error);

        NetworkResponse parseNetworkResponse(NetworkResponse response);
    }

}
