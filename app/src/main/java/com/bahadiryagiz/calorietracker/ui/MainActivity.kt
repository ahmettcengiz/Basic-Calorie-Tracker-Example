package com.bahadiryagiz.calorietracker.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahadiryagiz.calorietracker.R
import com.bahadiryagiz.calorietracker.retrofit.Food
import com.bahadiryagiz.calorietracker.retrofit.FoodListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var foodListViewModel: FoodListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foodListViewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        val total = intent.getStringExtra("totalCalorie")!!.toFloat()

        searchButton.setOnClickListener { set ->
            foodListViewModel.fetchNewFoodListData(foodSearchView.text.toString())
                .observe(this, { it ->

                    foodRecycleView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = FoodsAdapter(it, object : FoodsAdapter.OnClickListener {

                            override fun onItemClick(foods: Food) {
                                val intent = Intent(this@MainActivity, FoodDetailActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("foodLabel", foods.label)
                                bundle.putString("foodImage", foods.image)
                                bundle.putString("foodEnergy", foods.nutrients.ENERC_KCAL.toString())
                                bundle.putString("foodCho", foods.nutrients.CHOCDF.toString())
                                bundle.putString("foodProtein", foods.nutrients.PROCNT.toString())
                                bundle.putString("foodFat", foods.nutrients.FAT.toString())
                                intent.putExtra("totalCalorie", total.toString())
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }
                        })
                        if (!it.hints.isNullOrEmpty()) {
                            emptyTextView.visibility = View.INVISIBLE
                        }
                        else {
                            emptyTextView.visibility = View.VISIBLE
                        }
                    }
                })
        }

    }
}