package com.jiao.vhttp.http.interceptor;

import android.support.annotation.NonNull;

import com.jiao.vhttp.http.body.UploadProgressRequestBody;
import com.jiao.vhttp.http.callback.UCallback;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @Description : 上传进度拦截
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:42
 */

public class UploadProgressInterceptor implements Interceptor {
    private UCallback callback;

    public UploadProgressInterceptor(UCallback callback) {
        this.callback = callback;
        if (callback == null) {
            throw new NullPointerException("this callback must not null.");
        }
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if (originalRequest.body() == null) {
            return chain.proceed(originalRequest);
        }
        Request progressRequest = originalRequest.newBuilder()
                .method(originalRequest.method(),
                        new UploadProgressRequestBody(originalRequest.body(), callback))
                .build();
        return chain.proceed(progressRequest);
    }
}
