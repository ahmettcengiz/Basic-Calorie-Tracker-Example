package com.bahadiryagiz.calorietracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadiryagiz.calorietracker.R
import com.bahadiryagiz.calorietracker.retrofit.Food
import com.bahadiryagiz.calorietracker.retrofit.FoodModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.foods_row_design.view.*

class FoodsAdapter(
    private val foods: FoodModel,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<FoodsAdapter.ViewHolder>() {

    interface OnClickListener {
        fun onItemClick(foods: Food)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.foods_row_design, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foods.hints.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListener.onItemClick(foods.hints[position].food)
        }

        if(foods.hints[position].food.image != null) {
            Picasso.get().load(foods.hints[position].food.image).into(holder.itemView.foodImageView)
        }
        holder.itemView.foodNameTextView.text = foods.hints[position].food.label

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}