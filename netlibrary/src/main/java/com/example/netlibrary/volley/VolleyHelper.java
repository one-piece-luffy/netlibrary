package com.example.netlibrary.volley;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ClearCacheRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.netlibrary.BPConfig;
import com.example.netlibrary.BPListener;
import com.example.netlibrary.BPRequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by wanglin on 2017/10/31.
 * 必须在application oncreate里面 先调用 init（context），必须传入applicationcontext
 */

public class VolleyHelper {

    private static final String TAG = "VolleyHelper";

    private static final VolleyHelper ourInstance = new VolleyHelper();

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    private boolean inited;
    BPConfig config;

    public static VolleyHelper getInstance() {
        return ourInstance;
    }

    private VolleyHelper() {
    }

    /**
     * 参数传递 applicationContext
     *
     * @param config
     */
    public void init(BPConfig config) {
//        if (mRequestQueue == null) {
        OkHttp3Stack stack = new OkHttp3Stack(config);
        mRequestQueue = Volley.newRequestQueue(config.context, stack);
        this.config=config;
//        }
    }

    /**
     * @return The Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public byte[] getVolleyCacheData(String url) {
        if (mRequestQueue == null) {
            return null;
        }

        Cache cache = mRequestQueue.getCache();
        if (cache == null) {
            return null;
        }

        Cache.Entry entry = cache.get(url);
        if (entry == null) {
            return null;
        }
        return entry.data;
    }

    public byte[] getVolleyCacheDataForTime(String url, long time) {
        if (mRequestQueue == null) {
            return null;
        }

        Cache cache = mRequestQueue.getCache();
        if (cache == null) {
            return null;
        }

        Cache.Entry entry = cache.get(url);
        if (entry == null) {
            return null;
        }

        if (System.currentTimeMillis() > entry.softTtl + time) {
            return null;
        }
        return entry.data;
    }

    /**
     * 清除Volley缓存
     */
    public void clearVolleyCache() {
        if (mRequestQueue == null) {
            return;
        }

        Cache cache = mRequestQueue.getCache();
        if (cache == null) {
            return;
        }
        ClearCacheRequest req = new ClearCacheRequest(cache, new Runnable() {
            @Override
            public void run() {
            }
        });
        addToRequestQueue(req);
    }

    /**
     * Adds the specified request to the global queue, if tag is specified then
     * it is used else Default TAG is used.
     *
     * @param req
     * @param tag
     */
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (mRequestQueue == null) {
            Log.e(TAG, "网络库沒有初始化");
            return;
        }
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        VolleyLog.d("Adding request to queue: %s", req.getUrl());
        mRequestQueue.add(req);
    }

    /**
     * Adds the specified request to the global queue using the Default TAG.
     *
     * @param req
     */
    public <T> void addToRequestQueue(Request<T> req) {
        if (mRequestQueue == null) {
            Log.e(TAG, "网络库沒有初始化");
            return;
        }
        // set the default tag if tag is empty
        req.setTag(TAG);
        mRequestQueue.add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, it is important to
     * specify a TAG so that the pending/ongoing requests can be cancelled.
     *
     * @param tag
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    /**
     * 不应该强制 弄成空
     */
    public void clearRequestQueue() {
        if (mRequestQueue != null) {
            mRequestQueue.stop();
            mRequestQueue = null;
        }
    }

    public <E> void request(BPRequestBody<E> builder) {
        if (!TextUtils.isEmpty(builder.paramsJson)) {
            try {
                requestJsonRequest(builder.method,builder.url,builder.header,new JSONObject(builder.paramsJson),builder.requestTag,false,builder.onResponseString,builder.onException);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return;
        }
        if(builder.onResponse!=null){
            requestNesString(builder.method,builder.url,builder.header,builder.params,builder.requestTag,false,builder.onResponse,builder.onException);
            return;

        }
        if(builder.clazz!=null){
            requestGson(builder.method,builder.url,builder.header,builder.params,builder.clazz,builder.requestTag,false,builder.onResponseBean,builder.onException);
            return;
        }else {
            requestString(builder.method,builder.url,builder.header,builder.params,builder.requestTag,false,builder.onResponseString,builder.onException);
            return;
        }


    }

    public <E> void requestGson(int method, final String url, final Map<String, String> header, final Map<String, String> param, Class<E> mClass
            , String requestTag, boolean needCache, final BPListener.OnResponseBean<E> listener,final BPListener.OnException onException) {
        GsonRequest<E> req = new GsonRequest<E>(method, url, mClass, new Response.Listener<E>() {

            @Override
            public void onResponse(E response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (onException != null) {
                    onException.onException(error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        addToRequestQueue(req, requestTag);
    }

    public <E> void requestString(int method, final String url, final Map<String, String> header, final Map<String, String> param,
                                  String requestTag, boolean needCache, final BPListener.OnResponseString listener,final  BPListener.OnException onException) {
        StringRequest req = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (listener != null) {
                    listener.onResponse(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (onException != null) {
                    onException.onException(error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(config!=null&&config.header!=null){
                    header.putAll(config.header);
                }
                return header;

            }
        };
        Log.e(TAG,"from volley");

        addToRequestQueue(req, requestTag);
    }
    public <E> void requestNesString(int method, final String url, final Map<String, String> header, final Map<String, String> param,
                                  String requestTag, boolean needCache, final BPListener.OnResponse listener,final  BPListener.OnException onException) {
        StringRequestV2 req = new StringRequestV2(method, url, new ResponseListenerV2() {
            @Override
            public void onResponse(String response,Object o) {
                if (listener != null) {
                    listener.onResponse(response,o);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (onException != null) {
                    onException.onException(error.getMessage());
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return param;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(config!=null&&config.header!=null){
                    header.putAll(config.header);
                }
                return header;

            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };
        Log.e(TAG,"from volley");

        addToRequestQueue(req, requestTag);
    }

    public <E> void requestJsonRequest(int method, final String url, final Map<String, String> header, final JSONObject paramJsonObject,
                                       String requestTag, boolean needCache, final BPListener.OnResponseString listener,final BPListener.OnException onException) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, paramJsonObject, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onResponse(response.toString());
                }
            }
        }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (onException != null) {
                    onException.onException(error.getMessage());
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(config!=null&&config.header!=null){
                    header.putAll(config.header);
                }
                return header;
            }
        };

        addToRequestQueue(jsonObjectRequest, requestTag);
    }

    public <E> void requestJsonArrayRequest(int method, final String url, final Map<String, String> header, final JSONObject paramJsonObject,
                                       String requestTag, boolean needCache, final BPListener.OnResponseStrListener listener) {
        BaoFuJsoArraynRequest jsonArrayRequest=new BaoFuJsoArraynRequest(method,url,paramJsonObject.toString(),new  Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray s) {
                if (listener != null) {
                    listener.onResponse(s.toString());
                }
            }
        },new com.android.volley.Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onErrorResponse(error.getMessage());
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(config!=null&&config.header!=null){
                    header.putAll(config.header);
                }
                return header;
            }
        };


        addToRequestQueue(jsonArrayRequest, requestTag);
    }

    public <E> void requestForm(int method, final String url, final Map<String,String> header, final Map<String,String> param,
                                String requestTag, boolean needCache,  final BPListener.OnResponseStrListener listener) {
        FormRequest formRequest=new FormRequest(url, param, new FormRequest.SuccessListener() {
            @Override
            public void onResponses(String responseDate, Map header) {
                if(listener!=null){
                    listener.onResponse(responseDate);
                }
            }

            @Override
            public void onResponse(Object response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if(listener!=null){
                    listener.onErrorResponse(error.getMessage());
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if(config!=null&&config.header!=null){
                    header.putAll(config.header);
                }
                return header;
            }

        };

        addToRequestQueue(formRequest, requestTag);
    }


}
