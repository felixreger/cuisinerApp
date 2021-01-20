package com.stovia.cuisiner.viewmodel.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.stovia.cuisiner.R

class AdapterRecipeList (private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<AdapterRecipeList.ViewHolder>() {

    private var firstVisible = -1

    var recipeList = mutableListOf<String>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val  itemNombre: TextView = itemView.findViewById(R.id.nombreReceta)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view_recipe, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNombre.text = recipeList[position]
        //if (position == firstVisible) {
         //   holder.itemView.setBackgroundColor(Color.RED)
        //}
    }

    override fun getItemCount(): Int {
        return  recipeList.size
    }

    fun setDataList(recipe: MutableList<String>) {
        recipeList = recipe
    }

    fun getRecipeIndex(position: Int): String {
        return recipeList[position]
    }

    fun changeItem(position: Int) {
        firstVisible = position
        notifyItemChanged(firstVisible)
        notifyDataSetChanged()
    }
}