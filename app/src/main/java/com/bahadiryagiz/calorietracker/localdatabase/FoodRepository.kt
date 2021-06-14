package com.bahadiryagiz.calorietracker.localdatabase

import androidx.lifecycle.LiveData

class FoodRepository(private val foodDao: FoodDao) {

    val allFoodDetail: LiveData<List<FoodDetail>> = foodDao.findAll()

    fun insertFoodDetail(foodDetail: FoodDetail) {
        foodDao.insert(foodDetail)
    }

    fun deleteFoodDetail(foodDetail: FoodDetail) {
        foodDao.delete(foodDetail)
    }
}