package com.bahadiryagiz.calorietracker.ui

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bahadiryagiz.calorietracker.R
import kotlinx.android.synthetic.main.activity_bmicalculator.*
import kotlinx.android.synthetic.main.bmi_result_dialog.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BMICalculator : AppCompatActivity() {
    var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmicalculator)
        dialog = Dialog(this)
        calculateBtn.setOnClickListener {
            val bmi = calculateBMI()
            val result = displayBMI(bmi)
            openwinDialog(bmi, result)
        }


    }

    fun openwinDialog(bmi: Float, result: String) {
        dialog!!.setContentView(R.layout.bmi_result_dialog)
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.setCanceledOnTouchOutside(false);
        val continueButton: Button = dialog!!.findViewById(R.id.continueButton)
        val resultText: TextView = dialog!!.findViewById(R.id.result)
        val descriptionText: TextView = dialog!!.findViewById(R.id.textView)
        val image: ImageView = dialog!!.findViewById(R.id.imageView3)

        if(bmi < 18.5f) {
            image.setImageResource(R.drawable.ic_slim)
        }
        else if(bmi > 18.5f && bmi < 25f) {
            image.setImageResource(R.drawable.ic_waist)
        }
        else{
            image.setImageResource(R.drawable.ic_fat)
        }
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        resultText.text = df.format(bmi).toString() + " BMI"
        descriptionText.text = result

        dialog!!.show()
        continueButton.setOnClickListener() {
            dialog!!.dismiss()
            val intent = Intent(this@BMICalculator, OverviewActivity::class.java)
            intent.putExtra("totalCalorie",chooseDailyCalorie(bmi))
            startActivity(intent)
            finish()
        }
    }

    fun calculateBMI(): Float {
        var bmi: Float = 0F
        val heightStr: String = heightEditText.getText().toString()
        val weightStr: String = weightEditText.getText().toString()
        if (heightStr.isNotEmpty() && weightStr.isNotEmpty()) {
            val heightValue = heightStr.toFloat() / 100
            val weightValue = weightStr.toFloat()
            bmi = weightValue / (heightValue * heightValue)

        }

        return bmi
    }

    private fun displayBMI(bmi: Float): String {
        var bmiLabel = ""
        bmiLabel = if (java.lang.Float.compare(bmi, 15f) <= 0) {
            "VERY SEVERELY UNDERWEIGHT"
        } else if (java.lang.Float.compare(bmi, 15f) > 0 && java.lang.Float.compare(
                bmi,
                16f
            ) <= 0
        ) {
            "SEVERELY UNDERWEIGHT"
        } else if (java.lang.Float.compare(bmi, 16f) > 0 && java.lang.Float.compare(
                bmi,
                18.5f
            ) <= 0
        ) {
            "UNDERWEIGHT"
        } else if (java.lang.Float.compare(bmi, 18.5f) > 0 && java.lang.Float.compare(
                bmi,
                25f
            ) <= 0
        ) {
            "NORMAL"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            "OVERWEIGHT"
        } else if (java.lang.Float.compare(bmi, 30f) > 0 && java.lang.Float.compare(
                bmi,
                35f
            ) <= 0
        ) {
            "OBESE I"
        } else if (java.lang.Float.compare(bmi, 35f) > 0 && java.lang.Float.compare(
                bmi,
                40f
            ) <= 0
        ) {
            "OBESE II"
        } else {
            "OBESE III"
        }

        return bmiLabel
    }

    private fun chooseDailyCalorie(bmi:Float):String {
        var totalCalorie= 0f
        if(bmi < 20f) {
            totalCalorie = 4000f
        }
        else if(bmi > 20f && bmi < 30f) {
            totalCalorie = 3000f
        }
        else{
            totalCalorie = 2000f
        }

        return totalCalorie.toString()
    }
}

