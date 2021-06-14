package com.bahadiryagiz.calorietracker.retrofit

data class FoodModel(
    val hints: List<Hints>,
    val text: String
)

data class Hints(
    val food: Food
)

data class Food(
    val category: String,
    val categoryLabel: String,
    val foodId: String,
    val image: String?,
    val label: String,
    val nutrients: Nutrients
)

data class Nutrients(
    val CHOCDF: Double,
    val ENERC_KCAL: Double,
    val FAT: Double,
    val FIBTG: Double,
    val PROCNT: Double
)
