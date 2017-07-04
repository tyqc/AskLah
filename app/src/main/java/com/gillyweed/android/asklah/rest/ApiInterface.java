package com.gillyweed.android.asklah.rest;

import com.gillyweed.android.asklah.data.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public interface ApiInterface {

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @POST("user/login")
    Call<User> login(
            @Body User userBody,
            @Query("include") String includes);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @DELETE("user/logout")
    Call<ResponseBody> logout(@Header("Auth_Key") String accessToken);

    @Headers({
            "Api_Key: 08006c47-d0b9-4990-adb1-7d76610a4536",
            "Content-Type: application/json"})
    @PUT("user/update")
    Call<User> update(@Header("Auth_Key") String accessToken,@Body User newUsername);
}
