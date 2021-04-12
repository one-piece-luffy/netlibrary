使用：

1.在gradle 引入： 
api 'com.example.netlibrary:netlibrary:0.0.26'

2.在application 初始化

 BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTPS).build();
 BPRequest.getInstance().init(config);

3.请求：

 RequestStrategy mBaseModel = BPRequest.getInstance().mStrategy;
 

 BPRequestBody build = new BPRequestBody.Builder()

                .setMethod(BPRequest.Method.POST)

                .setParams(null)

                .setUrl("url")

                .setRequestTag("mRequestTag")

                .setOnResponseString(new BPListener.OnResponseString() {

                    @Override

                    public void onResponse(String s) {


                    }
                })
                .setOnException(new BPListener.OnException() {

                    @Override

                    public void onException(String s) {


                    }

                }).build();
        mBaseModel.request(build);
