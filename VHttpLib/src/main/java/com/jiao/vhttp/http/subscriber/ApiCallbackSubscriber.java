package com.jiao.vhttp.http.subscriber;

import com.jiao.vhttp.http.callback.ACallback;
import com.jiao.vhttp.http.exception.ApiException;

/**
 * @Description : 包含回调的订阅者，如果订阅这个，上层在不使用订阅者的情况下可获得回调
 * @Author : StriveJiao
 * @Date : 2018/2/4 16:42
 */

public class ApiCallbackSubscriber<T> extends ApiSubscriber<T> {
    ACallback<T> callBack;
    T data;

    public ApiCallbackSubscriber(ACallback<T> callBack) {
        if (callBack == null) {
            throw new NullPointerException("this callback is null!");
        }
        this.callBack = callBack;
    }

    @Override
    protected void onError(ApiException e) {
        if (e == null) {
            callBack.onFail(-1, "This ApiException is Null.");
            return;
        }
        callBack.onFail(e.getCode(), e.getMessage());
    }

    @Override
    public void onNext(T t) {
        this.data = t;
        callBack.onSuccess(t);
    }

    @Override
    public void onComplete() {

    }

    public T getData() {
        return data;
    }
}
