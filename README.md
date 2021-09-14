使用：

1.在根目录gradle加入：     maven { url 'https://jitpack.io' }


2.在项目gradle 引入： 
api 'com.example.netlibrary:netlibrary:1.4'

3.在application 初始化

 BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTPS).build();
 BPRequest.getInstance().init(config);

4.请求：

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
