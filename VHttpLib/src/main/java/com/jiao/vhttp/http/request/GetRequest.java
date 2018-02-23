package com.jiao.vhttp.http.request;

import com.jiao.vhttp.http.callback.ACallback;
import com.jiao.vhttp.http.core.ApiManager;
import com.jiao.vhttp.http.subscriber.ApiCallbackSubscriber;

import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

/**
 * @Description : Get请求
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:50
 */

public class GetRequest extends BaseHttpRequest<GetRequest> {
    public GetRequest(String suffixUrl) {
        super(suffixUrl);
    }

    @Override
    protected <T> Observable<T> execute(Type type) {
        return apiService.get(suffixUrl, params).compose(this.<T>norTransformer(type));
    }

    @Override
    protected <T> void execute(ACallback<T> callback) {
        DisposableObserver disposableObserver = new ApiCallbackSubscriber(callback);
        if (super.tag != null) {
            ApiManager.get().add(super.tag, disposableObserver);
        }
        this.execute(getType(callback)).subscribe(disposableObserver);
    }
}
