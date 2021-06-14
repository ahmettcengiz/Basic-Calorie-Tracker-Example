package com.bahadiryagiz.calorietracker.localdatabase

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    val allFoodDetail: LiveData<List<FoodDetail>>
    private val repository: FoodRepository

    init {
        val foodDao = Roomdb.getAppDatabase(application)?.foodDao()
        repository = FoodRepository(foodDao!!)
        allFoodDetail = repository.allFoodDetail
    }

    fun insertFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFoodDetail(foodDetail)
        }
    }

    fun deleteFoodDetail(foodDetail: FoodDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFoodDetail(foodDetail)
        }
    }

}