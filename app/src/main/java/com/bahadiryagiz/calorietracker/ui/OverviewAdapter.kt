package com.bahadiryagiz.calorietracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bahadiryagiz.calorietracker.R
import com.bahadiryagiz.calorietracker.localdatabase.FoodDetail
import kotlinx.android.synthetic.main.row_design_overview.view.*

class OverviewAdapter(
    private val foodList: List<FoodDetail>,
    private val onClickListenerFoodList: OnClickListenerFoodList,
    private val onClickListenerFoodListDelete: OnClickListenerFoodListDelete
) : RecyclerView.Adapter<OverviewAdapter.ViewHolder>() {

    // For the go food details
    interface OnClickListenerFoodList {
        fun onItemClick(foodDetail: FoodDetail)
    }

    // For the delete operations
    interface OnClickListenerFoodListDelete {
        fun onItemClickDelete(foodDetail: FoodDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_design_overview, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListenerFoodList.onItemClick(foodList[position])
        }

        val foodDetail = FoodDetail(
            foodList[position].ID,
            foodList[position].label,
            foodList[position].image,
            foodList[position].ENERC_KCAL,
            foodList[position].CHOCDF,
            foodList[position].PROCNT,
            foodList[position].FAT,
        )

        holder.itemView.btn_delete_item_overview.setOnClickListener {
            onClickListenerFoodListDelete.onItemClickDelete(foodDetail)
        }

        holder.itemView.tv_item_overview_name.text = foodList[position].label
        holder.itemView.tv_item_overview_kcal.text =
            foodList[position].ENERC_KCAL.toString() + "kcal"
        holder.itemView.tv_item_overview_carbs.text = foodList[position].CHOCDF.toString() + "g"
        holder.itemView.tv_item_overview_proteins.text = foodList[position].PROCNT.toString() + "g"
        holder.itemView.tv_item_overview_fats.text = foodList[position].FAT.toString() + "g"

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}