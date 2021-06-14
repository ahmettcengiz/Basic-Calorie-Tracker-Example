package com.bahadiryagiz.calorietracker.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://api.edamam.com/api/food-database/v2/"

object ApiClient {

    private var retrofitApiInterface: RetrofitApiInterface? = null

    fun build(): RetrofitApiInterface {
        val builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        val retrofit: Retrofit = builder.client(httpClient.build()).build()
        retrofitApiInterface = retrofit.create(
            RetrofitApiInterface::class.java
        )

        return retrofitApiInterface as RetrofitApiInterface
    }
}