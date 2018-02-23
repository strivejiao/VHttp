package com.jiao.vhttp.http.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @Description : 提供的系列接口
 * @Author : StriveJiao
 * @Date : 2018/2/4 15:53
 */

public interface ApiService {
    @GET()
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> post(@Url() String url, @FieldMap Map<String, String> maps);

    @FormUrlEncoded
    @POST()
    Observable<ResponseBody> postForm(@Url() String url, @FieldMap Map<String, Object> maps);

    @POST()
    Observable<ResponseBody> postBody(@Url() String url, @Body RequestBody requestBody);

    @Streaming
    @GET()
    Observable<ResponseBody> downFile(@Url() String url, @QueryMap Map<String, String> maps);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFiles(@Url() String url, @Part() List<MultipartBody.Part> parts);
}
