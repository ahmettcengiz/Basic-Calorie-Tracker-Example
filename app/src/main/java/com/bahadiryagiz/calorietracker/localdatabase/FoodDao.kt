package com.bahadiryagiz.calorietracker.localdatabase

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {
    @Query("SELECT * FROM FoodDetails ORDER BY label ASC")
    fun findAll(): LiveData<List<FoodDetail>>

    @Delete
    fun delete(foodDetail: FoodDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foodDetail: FoodDetail)
}

