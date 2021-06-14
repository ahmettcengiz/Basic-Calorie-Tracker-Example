package com.bahadiryagiz.calorietracker.retrofit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val APP_ID = "e60b5584"
private const val APP_KEY = "f5d3f3174efdfddc511e443f4a727709"

class FoodListViewModel : ViewModel() {
    private val _foodList = MutableLiveData<FoodModel>()
    private val foodList: LiveData<FoodModel>

    init {
        foodList = _foodList
    }

    fun fetchNewFoodListData(ingr: String): LiveData<FoodModel> {
        ApiClient.build().getFoodsWithIngr(APP_ID, APP_KEY, ingr)
            .enqueue(object : Callback<FoodModel> {

                override fun onFailure(call: Call<FoodModel>, t: Throwable) {
                    Log.e("onFailure", t.toString())
                }

                override fun onResponse(
                    call: Call<FoodModel>,
                    response: Response<FoodModel>
                ) {
                    //Log.e("onResponse", response.toString())
                    //Log.e("onResponse", response.body().toString())
                    _foodList.value = response.body()
                }
            })

        return foodList
    }
}