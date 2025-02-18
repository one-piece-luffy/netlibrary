# 使用：

1.在根目录gradle加入： maven { url 'https://jitpack.io' }


2.在项目gradle 引入： api 'com.example.netlibrary:netlibrary:2.2.0'

3.在application 初始化


    BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTP).build();  
    BPRequest.getInstance().init(config);

4.请求：

返回实体类

    BPRequest.getInstance()  
    .setMethod(BPRequest.Method.GET)  
    .setUrl("url")  
    .setParams(param)  
    .setHeader(header)  
    .setOnResponseBean(ConfigModelBean.class, new BPListener.OnResponseBean<ConfigModelBean>() {  
                    @Override  
                    public void onResponse(ConfigModelBean response) {  
                        Log.e("time",response.toString()+"");  
                    }  
                })  
                .setOnException(new BPListener.OnException() {  
                    @Override  
                    public void onException(Exception e, int code, String response) {  
                    }  
                })  
                .request();



   

   
