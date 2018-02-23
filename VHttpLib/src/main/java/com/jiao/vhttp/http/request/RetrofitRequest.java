package com.jiao.vhttp.http.request;

/**
 * @Description : 传入自定义Retrofit接口的请求类型
 * @Author : StriveJiao
 * @Date : 2018/2/4 16:49
 */

public class RetrofitRequest extends BaseRequest<RetrofitRequest> {

    public RetrofitRequest() {

    }

    public <T> T create(Class<T> cls) {
        generateGlobalConfig();
        return retrofit.create(cls);
    }
}
