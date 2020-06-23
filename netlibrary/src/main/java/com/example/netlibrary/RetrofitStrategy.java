package com.example.netlibrary;

import retrofit2.Retrofit;
import retrofit2.http.GET;

public class RetrofitStrategy implements HttpStrategy {

    @Override
    public void init() {

    }

    @GET("getIpInfo.php")
    @Override
    public String Get(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .build();
        String s=retrofit.create(String.class);

        return s;
    }

    @Override
    public String Post(String url) {
        return null;
    }

}
