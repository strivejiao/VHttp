package com.jiao.vhttp.http.common;

/**
 * @Description : 全局常量配置
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:11
 */

public class VConfig {

    public static final String API_HOST = "https://api.github.com/";//默认API主机地址

    public static final int DEFAULT_TIMEOUT = 10;//默认连接超时时间（秒）
    public static final int DEFAULT_WRITE_TIMEOUT = 10;//默认读取超时时间（秒）
    public static final int DEFAULT_READ_TIMEOUT = 10;//默认写入超时时间（秒）

    public static final int DEFAULT_RETRY_COUNT = 0;//默认重试次数
    public static final int DEFAULT_RETRY_DELAY_MILLIS = 1000;//默认重试间隔时间（毫秒）
    public static final String TAG = "VHttp";//日志输出标志
}
