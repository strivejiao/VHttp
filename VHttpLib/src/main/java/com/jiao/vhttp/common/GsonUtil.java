package com.jiao.vhttp.common;

import com.google.gson.Gson;

/**
 * @Description : Gson单例操作
 * @Author : StriveJiao
 * @Date : 2018/2/4 18:58
 */

public class GsonUtil {
    private static Gson gson;

    public static Gson gson() {
        if (gson == null) {
            synchronized (Gson.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
