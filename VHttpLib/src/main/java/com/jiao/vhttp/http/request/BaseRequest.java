package com.jiao.vhttp.http.request;

import com.jiao.vhttp.http.VHttp;
import com.jiao.vhttp.http.callback.UCallback;
import com.jiao.vhttp.http.config.HttpConfig;
import com.jiao.vhttp.http.interceptor.HeadersInterceptor;
import com.jiao.vhttp.http.interceptor.UploadProgressInterceptor;
import com.jiao.vhttp.http.mode.HttpHeaders;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @Description : 请求基类
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:25
 */

public abstract class BaseRequest<R extends BaseRequest> {
    protected HttpConfig httpGlobalConfig;//全局配置
    protected Retrofit retrofit;//Retrofit对象
    protected HttpHeaders headers = new HttpHeaders();//请求头
    protected UCallback uploadCallback;//上传进度回调
    protected Object tag;//请求标签

    /**
     * 设置请求标签
     *
     * @param tag
     * @return
     */
    public R tag(Object tag) {
        this.tag = tag;
        return (R) this;
    }


    /**
     * 1、生成全局配置
     */
    protected void generateGlobalConfig() {
        httpGlobalConfig = VHttp.CONFIG();

        VHttp.getRetrofitBuilder().baseUrl(httpGlobalConfig.getBaseUrl());
        VHttp.getOkHttpBuilder().connectTimeout(httpGlobalConfig.getConnectTimeout(), TimeUnit.SECONDS);
        VHttp.getOkHttpBuilder().readTimeout(httpGlobalConfig.getReadTimeout(), TimeUnit.SECONDS);
        VHttp.getOkHttpBuilder().writeTimeout(httpGlobalConfig.getWriteTimeout(), TimeUnit.SECONDS);
        VHttp.getRetrofitBuilder().addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        //暂时注掉 不确定作用
//        VHttp.getRetrofitBuilder().addConverterFactory(GsonConverterFactory.create());
//        VHttp.getOkHttpBuilder().hostnameVerifier(new SSLUtil.UnSafeHostnameVerifier(httpGlobalConfig.getBaseUrl()));
//        VHttp.getOkHttpBuilder().sslSocketFactory(SSLUtil.getSslSocketFactory(null, null, null));

        if (httpGlobalConfig.getGlobalHeaders() != null) {//设置请求头
            headers.put(httpGlobalConfig.getGlobalHeaders());
            VHttp.getOkHttpBuilder().addInterceptor(new HeadersInterceptor(headers.headersMap));
        }

        if (uploadCallback != null) {//设置上传回调
            VHttp.getOkHttpBuilder().addNetworkInterceptor(new UploadProgressInterceptor(uploadCallback));
        }

        if (httpGlobalConfig.getInterceptor() != null) {//设置拦截器
            VHttp.getOkHttpBuilder().addInterceptor(httpGlobalConfig.getInterceptor());
        }

        if (httpGlobalConfig.getNetInterceptor() != null) {//设置网络拦截器
            VHttp.getOkHttpBuilder().addNetworkInterceptor(httpGlobalConfig.getNetInterceptor());
        }

        VHttp.getRetrofitBuilder().client(VHttp.getOkHttpClient());
        retrofit = VHttp.getRetrofitBuilder().build();
    }

    /**
     * 获取第一级type
     *
     * @param t
     * @param <T>
     * @return
     */
    protected <T> Type getType(T t) {
        Type genType = t.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        Type type = params[0];
        Type finalNeedType;
        if (params.length > 1) {
            if (!(type instanceof ParameterizedType)) throw new IllegalStateException("没有填写泛型参数");
            finalNeedType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            finalNeedType = type;
        }
        return finalNeedType;
    }
}
