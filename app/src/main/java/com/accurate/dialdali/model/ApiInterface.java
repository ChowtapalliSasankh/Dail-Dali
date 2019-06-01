package com.accurate.dialdali.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface
{
    String BASE_URL = "http://foodieaku.com/dali_advertise/api/";
    @GET("api.php?method=getCategories")
    Call<List<Category>> getCategories();
    @GET("api.php?method=getInfoList&query=hotel&limit=0")
    Call<List<CategoryRow>>getInfoList(@Query("query") String category);
    @GET("api.php?method=getAdvertisement")
    Call<List<ImagesliderImage>>getAdvertisement();
    @GET("api.php?method=getNews&limit=0")
    Call<List<News>> getNews();
    @GET("api.php?method=registerUser")
    Call<List<RegisterResponse>> registerUser(@Query("name")String name,
                                        @Query("mobile_no")String mobile_no,
                                        @Query("email")String email);
}
