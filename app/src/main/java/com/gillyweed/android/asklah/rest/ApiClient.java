package com.gillyweed.android.asklah.rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Envy 15 on 28/6/2017.
 */

public class ApiClient {
    public static final String BASE_URL = "http://10.0.2.2:8000/api/v1/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient()
    {
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public okhttp3.Response intercept(Chain chain) throws IOException {
//
//                Request original = chain.request();
//
//                //request customization: add request headers
//                Request.Builder requestBuilder = original.newBuilder()
//                        .addHeader("Api_Key", "08006c47-d0b9-4990-adb1-7d76610a4536")
//                        .addHeader("Content-Type", "application/json");
//
//                Request request = requestBuilder.build();
//
//                return chain.proceed(request);
//            }
//        });
//
//        OkHttpClient client = httpClient.build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }
}
