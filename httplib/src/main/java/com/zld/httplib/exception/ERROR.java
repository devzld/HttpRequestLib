package com.zld.httplib.exception;

/**
 * 约定异常
 */

public class ERROR {
    /**
     * 未知错误
     */
    public static final int UNKNOWN = 1000;
    /**
     * 解析错误
     */
    public static final int PARSE_ERROR = 1001;
    /**
     * 网络错误
     */
    public static final int NETWORD_ERROR = 1002;
    /**
     * 协议出错
     */
    public static final int HTTP_ERROR = 1003;

    /**
     * 网络不可用，不请求
     */
    public static final int NO_NETWORK_ERROR = 10004;
}