package com.gillyweed.android.asklah.rest;

import com.gillyweed.android.asklah.data.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

//    @POST("user/update")
//    Call<User>
}
