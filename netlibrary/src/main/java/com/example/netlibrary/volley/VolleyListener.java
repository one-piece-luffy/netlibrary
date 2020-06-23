package com.example.netlibrary.volley;


import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * Created by wanglin on 2017/11/14.
 */

public interface VolleyListener {

    public interface OnResponseListener<E> {

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(VolleyError error);
    }

    public interface OnResponseStrListener {

        void onResponse(String response);

        void onCache(String response);

        void onNotModify();

        void onErrorResponse(VolleyError error);
    }

    public interface OnResponseListenerAsyn<E> {

        void doInBackground(NetworkResponse networkResponse, E response);

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(VolleyError error);
    }

    public interface OnResponseMultipartListener<E> {
        void onResponse(E response);

        void onErrorResponse(VolleyError error);

    }

    public interface OnResponseParseListener<E> {

        void onResponse(E response);

        void onCache(E response);

        void onNotModify();

        void onErrorResponse(VolleyError error);

        NetworkResponse parseNetworkResponse(NetworkResponse response);
    }

}
