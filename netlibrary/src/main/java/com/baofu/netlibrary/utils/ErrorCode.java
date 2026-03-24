package com.baofu.netlibrary.utils;


/**
 * 网络请求错误码定义
 */
public class ErrorCode {
    // 成功
    public static final int SUCCESS = 200;

    // 客户端错误 (1xxx)
    public static final int ERROR_URL_EMPTY = 1001;
    public static final int ERROR_URL_INVALID = 1002;
    public static final int ERROR_PARAM_NULL = 1003;
    public static final int ERROR_CANCELED = 1004;
    public static final int ERROR_TIMEOUT = 1005;

    // 服务端错误 (2xxx)
    public static final int ERROR_SERVER = 2001;
    public static final int ERROR_NOT_FOUND = 2004;
    public static final int ERROR_UNAUTHORIZED = 2001;

    // 解析错误 (3xxx)
    public static final int ERROR_JSON_PARSE = 3001;
    public static final int ERROR_RESPONSE_EMPTY = 3002;

    // 系统错误 (4xxx)
    public static final int ERROR_NETWORK = 4001;
    public static final int ERROR_UNKNOWN = 4999;

    // 缓存错误 (5xxx)
    public static final int ERROR_CACHE_READ = 5001;
    public static final int ERROR_CACHE_WRITE = 5002;
}