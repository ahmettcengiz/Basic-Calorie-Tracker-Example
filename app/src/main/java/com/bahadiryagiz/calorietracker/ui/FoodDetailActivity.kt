package com.bahadiryagiz.calorietracker.ui

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bahadiryagiz.calorietracker.R
import com.bahadiryagiz.calorietracker.localdatabase.FoodDetail
import com.bahadiryagiz.calorietracker.localdatabase.FoodViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_detail.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class FoodDetailActivity : AppCompatActivity() {
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        val total = intent.getStringExtra("totalCalorie")!!.toFloat()

        val foodLabel: String? = intent.getStringExtra("foodLabel")
        val foodImage: String? = intent.getStringExtra("foodImage")
        val foodEnergy: Double? = intent.getStringExtra("foodEnergy")?.toDouble()
        val foodCho: Double? = intent.getStringExtra("foodCho")?.toDouble()
        val foodProtein: Double? = intent.getStringExtra("foodProtein")?.toDouble()
        val foodFat: Double? = intent.getStringExtra("foodFat")?.toDouble()

        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING

        tv_total_kcal.text = df.format(foodEnergy).toString()
        detailFoodNameTextView.text = foodLabel
        Picasso.get().load(foodImage).into(detailFoodImageView)

        val totalNutrients: Int = doublesToIntOrOne(foodCho, foodProtein, foodFat)
        val carbsPercent: Int = (100 * (foodCho?.roundToInt()!!)) / totalNutrients
        val proteinPercent: Int = (100 * (foodProtein?.roundToInt()!!)) / totalNutrients
        val fatPercent: Int = (100 * foodFat?.roundToInt()!!) / totalNutrients
        val sumPercent: Int = carbsPercent + proteinPercent + fatPercent

        lly_chart.weightSum = (sumPercent + 2).toFloat()
        tv_current_carbs.text = df.format(foodCho).toString() + "g"
        tv_current_proteins.text = df.format(foodProtein).toString() + "g"
        tv_current_fats.text = df.format(foodFat).toString() + "g"

        tv_percent_carbs.text = "%" + df.format(carbsPercent)
        tv_percent_proteins.text = "%" + df.format(proteinPercent)
        tv_percent_fats.text = "%" + df.format(fatPercent)

        val param = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            carbsPercent.toFloat()
        )
        chart_value_carbs.layoutParams = param

        val param2 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            proteinPercent.toFloat()
        )
        chart_value_proteins.layoutParams = param2

        val param3 = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT,
            fatPercent.toFloat()
        )
        chart_value_fats.layoutParams = param3


        addToListButton.setOnClickListener { it1 ->
            foodViewModel.insertFoodDetail(
                FoodDetail(
                    0,
                    foodLabel!!,
                    foodImage,
                    foodEnergy!!,
                    foodCho,
                    foodProtein,
                    foodFat
                )
            )
            val intent = Intent(this@FoodDetailActivity, OverviewActivity::class.java)
            intent.putExtra("totalCalorie", total.toString())
            startActivity(intent)
            finish()
        }


    }

    private fun doublesToIntOrOne(a: Double?, b: Double?, c: Double?): Int {
        if ((a!! + b!! + c!!).roundToInt() > 0) {
            return (a + b + c).roundToInt()
        }
        return 1
    }
}