package com.bahadiryagiz.calorietracker.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bahadiryagiz.calorietracker.R
import com.bahadiryagiz.calorietracker.localdatabase.FoodDetail
import com.bahadiryagiz.calorietracker.localdatabase.FoodViewModel
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_overview.*
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class OverviewActivity : AppCompatActivity() {
    private lateinit var foodViewModel: FoodViewModel

    var total: Float = 2000.0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_overview)
        foodViewModel = ViewModelProvider(this)[FoodViewModel::class.java]
        total = intent.getStringExtra("totalCalorie")!!.toFloat()

        foodViewModel.allFoodDetail.observe(this, { it1 ->
            var foodEnergy = 0.0
            var foodCho = 0.0
            var foodProtein = 0.0
            var foodFat = 0.0

            for (item in it1) {
                foodEnergy += item.ENERC_KCAL
                foodCho += item.CHOCDF
                foodProtein += item.PROCNT
                foodFat += item.FAT
            }

            setupPieChart()
            loadPieChartData(foodEnergy.toFloat())

            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.CEILING
            tv_total_kcal2.text = df.format(foodEnergy).toString()

            val totalNutrients: Int = doublesToIntOrOne(foodCho, foodProtein, foodFat)
            val carbsPercent: Int = (100 * (foodCho.roundToInt())) / totalNutrients
            val proteinPercent: Int = (100 * (foodProtein.roundToInt())) / totalNutrients
            val fatPercent: Int = (100 * foodFat.roundToInt()) / totalNutrients
            val sumPercent: Int = carbsPercent + proteinPercent + fatPercent

            lly_chart2.weightSum = (sumPercent + 2).toFloat()
            tv_current_carbs2.text = df.format(foodCho).toString() + "g"
            tv_current_proteins2.text = df.format(foodProtein).toString() + "g"
            tv_current_fats2.text = df.format(foodFat).toString() + "g"

            tv_percent_carbs2.text = "%" + carbsPercent
            tv_percent_proteins2.text = "%" + proteinPercent
            tv_percent_fats2.text = "%" + fatPercent

            val param1 = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                carbsPercent.toFloat()
            )
            chart_value_carbs2.layoutParams = param1

            val param2 = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                proteinPercent.toFloat()
            )
            chart_value_proteins2.layoutParams = param2

            val param3 = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.MATCH_PARENT,
                fatPercent.toFloat()
            )
            chart_value_fats2.layoutParams = param3


            foodsRecycleView.apply {
                isNestedScrollingEnabled = false
                //setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@OverviewActivity)
                adapter = OverviewAdapter(it1, object : OverviewAdapter.OnClickListenerFoodList {
                    override fun onItemClick(foodDetail: FoodDetail) {
                        val intent = Intent(this@OverviewActivity, FoodDetailActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("foodLabel", foodDetail.label)
                        bundle.putString("foodImage", foodDetail.image)
                        bundle.putString("foodEnergy", foodDetail.ENERC_KCAL.toString())
                        bundle.putString("foodCho", foodDetail.CHOCDF.toString())
                        bundle.putString("foodProtein", foodDetail.PROCNT.toString())
                        bundle.putString("foodFat", foodDetail.FAT.toString())
                        intent.putExtra("totalCalorie", total.toString())
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }, object : OverviewAdapter.OnClickListenerFoodListDelete {
                    override fun onItemClickDelete(foodDetail: FoodDetail) {
                        foodViewModel.deleteFoodDetail(foodDetail)
                        setupPieChart()
                        loadPieChartData(foodEnergy.toFloat())

                    }
                })
            }
        })


        addFoodBUtton.setOnClickListener {
            val intent = Intent(this@OverviewActivity, MainActivity::class.java)
            intent.putExtra("totalCalorie", total.toString())
            startActivity(intent)
        }
    }

    private fun doublesToIntOrOne(a: Double?, b: Double?, c: Double?): Int {
        if ((a!! + b!! + c!!).roundToInt() > 0) {
            return (a + b + c).roundToInt()
        }
        return 1
    }

    private fun setupPieChart() {
        pieChart.setDrawHoleEnabled(true)
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12F)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.setCenterTextSize(24F)
        pieChart.getDescription().setEnabled(false)
        val l: Legend = pieChart.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.isEnabled = true
    }

    private fun loadPieChartData(temp: Float) {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(temp, "Total Calorie"))
        entries.add(PieEntry(total - temp, "Rest Calorie"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }


}