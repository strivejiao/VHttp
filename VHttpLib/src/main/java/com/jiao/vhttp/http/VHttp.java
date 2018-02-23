package com.jiao.vhttp.http;

import android.content.Context;

import com.jiao.vhttp.http.callback.UCallback;
import com.jiao.vhttp.http.config.HttpConfig;
import com.jiao.vhttp.http.core.ApiManager;
import com.jiao.vhttp.http.request.BaseHttpRequest;
import com.jiao.vhttp.http.request.GetRequest;
import com.jiao.vhttp.http.request.PostRequest;
import com.jiao.vhttp.http.request.RetrofitRequest;
import com.jiao.vhttp.http.request.UploadRequest;

import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @Description : 网络请求入口
 * @Author : StriveJiao
 * @Date : 2018/2/4 14:54
 */

public class VHttp {
    private static Context mContext;
    private static OkHttpClient.Builder okHttpBuilder;
    private static Retrofit.Builder retrofitBuilder;
    private static OkHttpClient okHttpClient;

    private static final HttpConfig NET_CONFIG = HttpConfig.getInstance();

    public static void init(Context appContext) {
        if (mContext == null && appContext != null) {
            mContext = appContext.getApplicationContext();
            okHttpBuilder = new OkHttpClient.Builder();
            retrofitBuilder = new Retrofit.Builder();
        }
    }

    public static HttpConfig CONFIG() {
        return NET_CONFIG;
    }

    public static Context getContext() {
        if (mContext == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return mContext;
    }

    public static OkHttpClient.Builder getOkHttpBuilder() {
        if (okHttpBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return okHttpBuilder;
    }

    public static Retrofit.Builder getRetrofitBuilder() {
        if (retrofitBuilder == null) {
            throw new IllegalStateException("Please call ViseHttp.init(this) in Application to initialize!");
        }
        return retrofitBuilder;
    }


    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = getOkHttpBuilder().build();
        }
        return okHttpClient;
    }


    /**
     * 通用请求，可传入自定义请求
     *
     * @param request
     * @return
     */
    public static BaseHttpRequest BASE(BaseHttpRequest request) {
        if (request != null) {
            return request;
        } else {
            return new GetRequest("");
        }
    }

    /**
     * 可传入自定义Retrofit接口服务的请求类型
     *
     * @return
     */
    public static <T> RetrofitRequest RETROFIT() {
        return new RetrofitRequest();
    }

    /**
     * GET请求
     *
     * @param suffixUrl
     * @return
     */
    public static GetRequest GET(String suffixUrl) {
        return new GetRequest(suffixUrl);
    }

    /**
     * POST请求
     *
     * @param suffixUrl
     * @return
     */
    public static PostRequest POST(String suffixUrl) {
        return new PostRequest(suffixUrl);
    }


    /**
     * 上传
     *
     * @param suffixUrl
     * @return
     */
    public static UploadRequest UPLOAD(String suffixUrl) {
        return new UploadRequest(suffixUrl);
    }

    /**
     * 上传（包含上传进度回调）
     *
     * @param suffixUrl
     * @return
     */
    public static UploadRequest UPLOAD(String suffixUrl, UCallback uCallback) {
        return new UploadRequest(suffixUrl, uCallback);
    }


    /**
     * 添加请求订阅者
     *
     * @param tag
     * @param disposable
     */
    public static void addDisposable(Object tag, Disposable disposable) {
        ApiManager.get().add(tag, disposable);
    }

    /**
     * 根据Tag取消请求
     */
    public static void cancelTag(Object tag) {
        ApiManager.get().cancel(tag);
    }

    /**
     * 取消所有请求请求
     */
    public static void cancelAll() {
        ApiManager.get().cancelAll();
    }


}
