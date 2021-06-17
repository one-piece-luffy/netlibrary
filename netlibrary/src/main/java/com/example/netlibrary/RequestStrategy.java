package com.example.netlibrary;

public interface RequestStrategy {

    public void init(BPConfig config);

    public void cancelRequests(Object tag);

    public void request(BPRequestBody builder);


}
