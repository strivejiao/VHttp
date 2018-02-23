package com.jiao.vhttp.http.request;

import android.text.TextUtils;

import com.jiao.vhttp.http.VHttp;
import com.jiao.vhttp.http.api.ApiService;
import com.jiao.vhttp.http.callback.ACallback;
import com.jiao.vhttp.http.func.ApiFunc;
import com.jiao.vhttp.http.func.ApiRetryFunc;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @Description : 通用请求基类
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:24
 */

public abstract class BaseHttpRequest<R extends BaseHttpRequest> extends BaseRequest<R> {
    protected ApiService apiService;//通用接口服务
    protected Map<String, String> params = new LinkedHashMap<>();//请求参数
    protected String suffixUrl = "";//链接后缀
    protected int retryDelayMillis;//请求失败重试间隔时间
    protected int retryCount;//重试次数

    public BaseHttpRequest() {
    }

    public BaseHttpRequest(String suffixUrl) {
        if (!TextUtils.isEmpty(suffixUrl)) {
            this.suffixUrl = suffixUrl;
        }
    }

    public <T> Observable<T> request(Type type) {
        generateGlobalConfig();
        return execute(type);
    }

    public <T> void request(ACallback<T> callback) {
        generateGlobalConfig();
        execute(callback);
    }

    @Override
    protected void generateGlobalConfig() {
        super.generateGlobalConfig();
        if (httpGlobalConfig.getGlobalParams() != null) {
            params.putAll(httpGlobalConfig.getGlobalParams());
        }
        if (retryCount <= 0) {
            retryCount = httpGlobalConfig.getRetryCount();
        }
        if (retryDelayMillis <= 0) {
            retryDelayMillis = httpGlobalConfig.getRetryDelayMillis();
        }
        apiService = retrofit.create(ApiService.class);
    }

    protected abstract <T> Observable<T> execute(Type type);

    protected abstract <T> void execute(ACallback<T> callback);

    protected <T> ObservableTransformer<ResponseBody, T> norTransformer(final Type type) {
        return new ObservableTransformer<ResponseBody, T>() {
            @Override
            public ObservableSource<T> apply(Observable<ResponseBody> apiResultObservable) {
                return apiResultObservable
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new ApiFunc<T>(type))
                        .retryWhen(new ApiRetryFunc(retryCount, retryDelayMillis));
            }
        };
    }

    /**
     * 添加请求参数
     *
     * @param paramKey
     * @param paramValue
     * @return
     */
    public R addParam(String paramKey, String paramValue) {
        if (paramKey != null && paramValue != null) {
            this.params.put(paramKey, paramValue);
        }
        return (R) this;
    }

    /**
     * 添加请求参数
     *
     * @param params
     * @return
     */
    public R addParams(Map<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
        return (R) this;
    }

    /**
     * 移除请求参数
     *
     * @param paramKey
     * @return
     */
    public R removeParam(String paramKey) {
        if (paramKey != null) {
            this.params.remove(paramKey);
        }
        return (R) this;
    }

    /**
     * 设置请求参数
     *
     * @param params
     * @return
     */
    public R params(Map<String, String> params) {
        if (params != null) {
            this.params = params;
        }
        return (R) this;
    }

    /**
     * 设置请求失败重试间隔时间（毫秒）
     *
     * @param retryDelayMillis
     * @return
     */
    public R retryDelayMillis(int retryDelayMillis) {
        this.retryDelayMillis = retryDelayMillis;
        return (R) this;
    }

    /**
     * 设置请求失败重试次数
     *
     * @param retryCount
     * @return
     */
    public R retryCount(int retryCount) {
        this.retryCount = retryCount;
        return (R) this;
    }


    public String getSuffixUrl() {
        return suffixUrl;
    }

    public int getRetryDelayMillis() {
        return retryDelayMillis;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Map<String, String> getParams() {
        return params;
    }


}
