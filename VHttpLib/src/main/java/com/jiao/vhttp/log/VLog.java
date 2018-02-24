package com.jiao.vhttp.log;

import android.util.Log;

import com.jiao.vhttp.http.common.VConfig;
import com.jiao.vhttp.http.config.HttpConfig;

/**
 * @Description : 日志输出类
 * @Author : StriveJiao
 * @Date : 2018/2/24 15:29
 */

public class VLog {

    public static void e(String message) {
        if (!HttpConfig.getInstance().getIsLog())
            return;
        Log.e(VConfig.TAG, message);
    }

    public static void i(String message) {
        if (!HttpConfig.getInstance().getIsLog())
            return;
        Log.i(VConfig.TAG, message);
    }

    public static void d(String message) {
        if (!HttpConfig.getInstance().getIsLog())
            return;
        Log.d(VConfig.TAG, message);
    }
}
