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

返回字符串

    BPRequest.getInstance()  
    .setMethod(BPRequest.Method.GET)  
    .setUrl("url")  
    .setParams(param)  
    .setHeader(header)  
    .setOnResponseString(new BPListener.OnResponseString() {  
        @Override  
        public void onResponse(String response) {  
            
        }  
    })  
    .setOnException(new BPListener.OnException() {  
        @Override  
        public void onException(Exception e, int code, String response) {  
        }  
    })  
    .request();



### 不同网络请求框架之间的对比

|  功能或细节  | [netlibrary](https://github.com/getActivity/EasyHttp) | [Retrofit](https://github.com/square/retrofit)  |
| :----: | :------: |  :-----: |  :-----: |
|    对应版本  |  13.0 |  2.9.0  |  3.0.4    |
|    issues 数   |  [![](https://img.shields.io/github/issues/getActivity/EasyHttp.svg)](https://github.com/getActivity/EasyHttp/issues)  |  [![](https://img.shields.io/github/issues/square/retrofit.svg)](https://github.com/square/retrofit/issues)  |
|    **动态 Host**  |  ✅  |  ❌     |
|    全局参数   |  ✅  |  ❌    |
|    响应统一处理   |  ✅  |  ✅     |
|    超时重试   |  ✅  |  ✅     |
|    **配置 Http 缓存**   |  ✅  |  ❌  |
|    Json 参数提交  |  ✅  |   ❌     |
|    **请求生命周期**  | 自动管控 |   需要封装  |
|    框架灵活性  |    高     |     低          |
|    框架学习成本   |    中    |     高      |
|    **API 记忆成本**  |    低    |     高     |
|    **接口维护成本**   |   低     |     中     |
|    框架维护状态   |  维护中  |   维护中    |



   

   
