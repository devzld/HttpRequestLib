package com.zld.httplib.service;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * <pre>
 *     @author : lingdong
 *     @e-mail : 779724606@qq.com
 *     @date   : 2018/08/07
 *     @desc   :
 * </pre>
 */
public interface CommonService {

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    /******  post *******/
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    //todo 检查header是否覆盖
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<String> postJson(@Url String url, @Body RequestBody body, @HeaderMap Map<String, String> headers);

    /******  put *******/
    @FormUrlEncoded
    @PUT
    Observable<String> put(@Url String url, @FieldMap Map<String, Object> params, @HeaderMap Map<String, String> headers);

    //todo 检查header是否覆盖
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @PUT
    Observable<String> putJson(@Url String url, @Body RequestBody body, @HeaderMap Map<String, String> headers);

    @DELETE
    Observable<String> delete(@Url String url, @QueryMap Map<String, Object> params, @HeaderMap Map<String, String> headers);
}
