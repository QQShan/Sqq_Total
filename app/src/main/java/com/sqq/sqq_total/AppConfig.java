package com.sqq.sqq_total;

/**
 * Created by sqq on 2016/6/8.
 */
public class AppConfig {
    /**
     * 请求报文头部属性名称
     */
    public static String Authorization = "Authorization";

    /**
     * 缓存大小
     */
    public static final int HTTP_RESPONSE_DISK_CACHE_MAX_SIZE = 10*1024*1024;

    public static final String HTTP_RESPONSE_DISK_CACHE_PATH = "HttpResPonseCache";

    /**
     * retrofit2.0后baseurl必须要以/结尾
     */
    public static String BaseURL="http://10.0.3.114/AndroidService/";
}
