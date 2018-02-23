package com.jiao.vhttp.http.callback;

/**
 * @Description : 请求接口回调
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:29
 */

public abstract class ACallback<T> {
    public abstract void onSuccess(T data);

    public abstract void onFail(int errCode, String errMsg);
}
