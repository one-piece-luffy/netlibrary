package com.baofu.netlibrary;

public interface RequestStrategy {

    public void init(BPConfig config);

    public void cancelRequests(Object tag);

    public void request(BPRequestBody builder);

    public <T> T requestSync(BPRequestBody<T> builder);
    public String requestStringSync(BPRequestBody builder);


}
