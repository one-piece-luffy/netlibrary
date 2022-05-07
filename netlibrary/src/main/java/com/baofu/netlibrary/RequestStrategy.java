package com.baofu.netlibrary;

import okhttp3.Response;

public interface RequestStrategy {

    public void init(BPConfig config);

    public void cancelRequests(Object tag);

    public void request(BPRequestBody builder);

    public Response requestSync(BPRequestBody builder);


}
