package com.jiao.vhttp.http.subscriber;

import com.jiao.vhttp.http.exception.ApiException;
import com.jiao.vhttp.http.mode.ApiCode;

import io.reactivex.observers.DisposableObserver;

/**
 * @Description : API统一订阅者
 * @Author : StriveJiao
 * @Date : 2018/2/4 16:42
 */

abstract class ApiSubscriber<T> extends DisposableObserver<T> {

    ApiSubscriber() {

    }

    //异常统一处理
    @Override
    public void onError(Throwable e) {
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, ApiCode.Request.UNKNOWN));
        }
    }

    protected abstract void onError(ApiException e);
}
