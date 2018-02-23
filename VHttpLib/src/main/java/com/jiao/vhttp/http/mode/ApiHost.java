package com.jiao.vhttp.http.mode;

import com.jiao.vhttp.http.common.VConfig;

/**
 * @Description : 主机信息
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:10
 */

public class ApiHost {
    private static String host = VConfig.API_HOST;

    public static String getHost() {
        return host;
    }

    public static void setHost(String url) {
        setHostHttps(url);
    }

    public static void setHostHttp(String url) {
        if (url.startsWith("https://") || url.startsWith("http://")) {
            host = url;
            host = host.replaceAll("https://", "http://");
        } else {
            host = "http://" + url;
        }
    }

    public static void setHostHttps(String url) {
        if (url.startsWith("https://") || url.startsWith("http://")) {
            host = url;
            host = host.replaceAll("http://", "https://");
        } else {
            host = "https://" + url;
        }
    }

    public static String getHttp() {
        if (host.startsWith("https://") || host.startsWith("http://")) {
            host = host.replaceAll("https://", "http://");
        } else {
            host = "http://" + host;
        }
        return host;
    }

    public static String getHttps() {
        if (host.startsWith("https://") || host.startsWith("http://")) {
            host = host.replaceAll("http://", "https://");
        } else {
            host = "https://" + host;
        }
        return host;
    }
}
