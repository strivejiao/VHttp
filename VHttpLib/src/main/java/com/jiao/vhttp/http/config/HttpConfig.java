package com.jiao.vhttp.http.config;

import com.jiao.vhttp.http.common.VConfig;
import com.jiao.vhttp.http.mode.ApiHost;

import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Interceptor;

/**
 * @Description : 网络请求全局配置
 * @Author : StriveJiao
 * @Date : 2018/2/4 14:59
 */

public class HttpConfig {
    private Interceptor interceptor;//拦截器
    private Interceptor netInterceptor;//网络拦截器
    private Map<String, String> globalParams = new LinkedHashMap<>();//请求参数
    private Map<String, String> globalHeaders = new LinkedHashMap<>();//请求头
    private String baseUrl;//基础域名
    private boolean isLog = false;//是否输出日志
    private int retryDelayMillis;//请求失败重试间隔时间
    private int retryCount;//请求失败重试次数
    private int writeTimeout;//写入超时时间
    private int readTimeout;//读取超时时间
    private int connectTimeout;//连接超时时间

    private HttpConfig() {
    }

    private static HttpConfig mInstance;

    public static HttpConfig getInstance() {
        if (mInstance == null) {
            synchronized (HttpConfig.class) {
                if (mInstance == null) {
                    mInstance = new HttpConfig();
                }
            }
        }
        return mInstance;
    }

    //设置请求头
    public HttpConfig httpHeaders(Map<String, String> globalHeaders) {
        if (globalHeaders != null) {
            this.globalHeaders = globalHeaders;
        }
        return this;
    }

    //设置请求参数
    public HttpConfig httpParams(Map<String, String> globalParams) {
        if (globalParams != null) {
            this.globalParams = globalParams;
        }
        return this;
    }

    //设置baseUrl
    public HttpConfig baseUrl(String baseUrl) {
        this.baseUrl = checkNotNull(baseUrl, "baseUrl == null");
        ApiHost.setHost(this.baseUrl);
        return this;
    }

    //设置请求失败重试间隔时间
    public HttpConfig retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return this;
    }

    //设置请求失败重试次数
    public HttpConfig retryCount(int retryCount) {
        this.retryCount = retryCount;
        return this;
    }

    //设置写入超时时间
    public HttpConfig writeTimeout(int timeout) {
        this.writeTimeout = timeout;
        return this;
    }

    //设置读取超时时间
    public HttpConfig readTimeout(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    //设置连接超时时间
    public HttpConfig connectTimeout(int timeout) {
        this.connectTimeout = timeout;
        return this;
    }

    //设置连接超时时间
    public HttpConfig log(boolean isLog) {
        this.isLog = isLog;
        return this;
    }

    //设置拦截器
    public HttpConfig interceptor(Interceptor interceptor) {
        this.interceptor = checkNotNull(interceptor, "interceptor == null");
        return this;
    }

    //设置网络拦截器
    public HttpConfig networkInterceptor(Interceptor interceptor) {
        this.netInterceptor = checkNotNull(interceptor, "interceptor == null");
        return this;
    }

    //获取参数
    public Map<String, String> getGlobalParams() {
        return globalParams;
    }

    //获取baseUrl
    public String getBaseUrl() {
        return baseUrl;
    }

    //获取headers
    public Map<String, String> getGlobalHeaders() {
        return globalHeaders;
    }

    //获取写入超时时间
    public int getWriteTimeout() {
        return writeTimeout > 0 ? writeTimeout : VConfig.DEFAULT_WRITE_TIMEOUT;
    }

    //获取读取超时时间
    public int getReadTimeout() {
        return readTimeout > 0 ? readTimeout : VConfig.DEFAULT_READ_TIMEOUT;
    }

    //获取连接超时时间
    public int getConnectTimeout() {
        return connectTimeout > 0 ? connectTimeout : VConfig.DEFAULT_TIMEOUT;
    }

    //获取是否输出日志
    public boolean getIsLog() {
        return isLog;
    }

    //获取拦截器
    public Interceptor getInterceptor() {
        return interceptor;
    }

    //获取网络拦截器
    public Interceptor getNetInterceptor() {
        return netInterceptor;
    }

    //获取断开重连时间
    public int getRetryDelayMillis() {
        if (retryDelayMillis <= 0) {
            retryDelayMillis = VConfig.DEFAULT_RETRY_DELAY_MILLIS;
        }
        return retryDelayMillis;
    }

    //获取断开重试次数
    public int getRetryCount() {
        if (retryCount <= 0) {
            retryCount = VConfig.DEFAULT_RETRY_COUNT;
        }
        return retryCount;
    }

    //检查是否为null
    private <T> T checkNotNull(T t, String message) {
        if (t == null) {
            throw new NullPointerException(message);
        }
        return t;
    }
}
