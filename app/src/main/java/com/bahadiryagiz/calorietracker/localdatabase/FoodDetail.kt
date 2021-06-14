package com.bahadiryagiz.calorietracker.localdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "FoodDetails")
data class FoodDetail(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "ID") val ID: Int,
    @ColumnInfo(name = "label") val label: String,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "ENERC_KCAL") val ENERC_KCAL: Double,
    @ColumnInfo(name = "CHOCDF") val CHOCDF: Double,
    @ColumnInfo(name = "PROCNT") val PROCNT: Double,
    @ColumnInfo(name = "FAT") val FAT: Double,
)