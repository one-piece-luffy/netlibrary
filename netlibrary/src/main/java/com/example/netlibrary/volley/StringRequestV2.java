package com.example.netlibrary.volley;

import androidx.annotation.GuardedBy;
import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringRequestV2 extends Request<String> { /** Lock to guard mListener as it is cleared on cancel() and read on delivery. */
private final Object mLock = new Object();

    @Nullable
    @GuardedBy("mLock")
    private ResponseListenerV2 mListener;
    Map<String, String> mHeaders;

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequestV2(
            int method,
            String url,
            ResponseListenerV2 listener,
            @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }

    /**
     * Creates a new GET request.
     *
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public StringRequestV2(
            String url, ResponseListenerV2 listener, @Nullable Response.ErrorListener errorListener) {
        this(Method.GET, url, listener, errorListener);
    }

    @Override
    public void cancel() {
        super.cancel();
        synchronized (mLock) {
            mListener = null;
        }
    }

    @Override
    protected void deliverResponse(String response) {
        ResponseListenerV2 listener;
        synchronized (mLock) {
            listener = mListener;
        }
        if (listener != null) {
            listener.onResponse(response,mHeaders);
        }
    }

    @Override
    @SuppressWarnings("DefaultCharset")
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            List<Header> list=response.allHeaders;
            mHeaders=new HashMap<>();
            for(int i=0;i<list.size();i++){
                mHeaders.put(list.get(i).getName(),list.get(i).getValue());
            }

        } catch (UnsupportedEncodingException e) {
            // Since minSdkVersion = 8, we can't call
            // new String(response.data, Charset.defaultCharset())
            // So suppress the warning instead.
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }


}
