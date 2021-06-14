package com.bahadiryagiz.calorietracker.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApiInterface {

    @GET("parser")
    fun getFoodsWithIngr(
        @Query("app_id") app_id: String,
        @Query("app_key") app_key: String,
        @Query("ingr") ingr: String,
    ): Call<FoodModel>
}