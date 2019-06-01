package com.accurate.dialdali.utility;

import com.accurate.dialdali.model.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class AppUtil
{
    public static Retrofit getRetrofitObj( )
    {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
          Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).
                        addConverterFactory(GsonConverterFactory.create(gson)).build();
            return retrofit;

    }

}
