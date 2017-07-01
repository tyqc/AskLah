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
