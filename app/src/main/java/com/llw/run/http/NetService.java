package com.llw.run.http;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetService {

    @GET("queryDynamic")
    Call<JsonArray> getDynamic();

    @Headers({"content-type:application/json;charset=UTF-8"})
    @POST("insertDynamic")
    Call<BaseDataResponse> insertDynamic(@Body JsonObject jsonObject);

    @Headers({"content-type:application/json;charset=UTF-8"})
    @POST("PublishComment")
    Call<BaseDataResponse> replay(@Body JsonObject jsonObject);

}
