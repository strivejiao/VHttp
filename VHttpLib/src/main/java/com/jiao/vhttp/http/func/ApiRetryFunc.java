package com.jiao.vhttp.http.func;

import com.jiao.vhttp.http.exception.ApiException;
import com.jiao.vhttp.log.VLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * @Description : 重试机制
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:57
 */

public class ApiRetryFunc implements Function<Observable<? extends Throwable>, Observable<?>> {
    private final int maxRetries;
    private final int retryDelayMillis;
    private int retryCount;

    public ApiRetryFunc(int maxRetries, int retryDelayMillis) {
        this.maxRetries = maxRetries;
        this.retryDelayMillis = retryDelayMillis;
    }

    @Override
    public Observable<?> apply(Observable<? extends Throwable> observable) throws Exception {
        return observable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries && (throwable instanceof SocketTimeoutException
                                || throwable instanceof ConnectException)) {
                            VLog.d("get response data error, it will try after " + retryDelayMillis
                                    + " millisecond, retry count " + retryCount);
                            return Observable.timer(retryDelayMillis, TimeUnit.MILLISECONDS);
                        }
                        return Observable.error(ApiException.handleException(throwable));
                    }
                });
    }
}
