使用：

1.在gradle 引入： 
api 'com.example.netlibrary:netlibrary:0.0.26'

2.在application 初始化
 BPConfig config=new BPConfig.Builder().context(this).strategyType(BPRequest.STRATEGY_TYPE.OKHTTPS).build();
 BPRequest.getInstance().init(config);
