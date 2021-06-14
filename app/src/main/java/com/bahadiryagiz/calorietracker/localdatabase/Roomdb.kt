package com.bahadiryagiz.calorietracker.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [FoodDetail::class], version = 1)
abstract class Roomdb : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        private var INSTANCE: Roomdb? = null
        fun getAppDatabase(context: Context): Roomdb? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<Roomdb>(
                    context.applicationContext, Roomdb::class.java, "FoodDB"
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return INSTANCE
        }
    }
}