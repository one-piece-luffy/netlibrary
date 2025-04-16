### 不同网络请求框架之间的对比
|     功能或细节      | [netlibrary](https://github.com/one-piece-luffy/netlibrary) | [Retrofit](https://github.com/square/retrofit)  |       |
|:--------------:|:---------------:|  :-----: |:-----------------------------------------:|
|  **动态 Host**   |        ✅        |  ❌  |
|      全局参数      |        ✅        |  ❌  |
| **配置 Http 缓存** |        ✅        |  ❌  |
|     响应统一处理     |        ✅        |   ❌   |
|   **请求生命周期**   |      自动管控       |   需要封装  | 
|     框架灵活性      |        高        |     低      |
|     框架学习成本     |        中        |     高    |
|  **API 记忆成本**  |        低        |     高    |
|   **接口维护成本**   |        低        |     中    |
|     框架维护状态     |       维护中       |   维护中   |


* Retrofit 在我看来并不是那么好用，因为很多常用的功能实现起来比较麻烦，动态 Host 要写拦截器，日志打印要写拦截器，就连最常用的添加全局参数也要写拦截器，一个拦截器意味着要写很多代码，如果写得不够严谨还有可能出现 Bug，从而影响整个 OkHttp 请求流程，我经常在想这些功能能不能都用一句代码搞定，因为我觉得这些功能是设计框架的时候本应该考虑的，这便是我做这个框架的初心。


*

# 使用：

1.在根目录gradle加入： maven { url 'https://jitpack.io' }


2.在项目gradle 引入： api 'com.example.netlibrary:netlibrary:2.2.0'

3.在application 初始化


     BPConfig bpConfig =
                new BPConfig.Builder().context(this)
                        .strategyType(BPRequest.STRATEGY_TYPE.OKHTTP)
                        .timeout(10)
                        .banProxy(true)
                        .addHeader(globalHeader)
                        .build();
    BPRequest.getInstance().init(bpConfig);

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









   

   
