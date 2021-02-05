package com.stovia.cuisiner.viewmodel.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Recipe

class AdapterRecipe(private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<AdapterRecipe.ViewHolder>() {

    var recipeList = mutableListOf<Recipe>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        val  itemNombre: TextView = itemView.findViewById(R.id.nombreReceta)

        val itemLayout : RelativeLayout = itemView.findViewById(R.id.itemRelativeLayout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                notifyDataSetChanged()
                listener.onItemClick(position)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                notifyDataSetChanged()
                listener.onLongClick(position)
                return true
            }
            return false
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
        fun onLongClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view_recipe, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(getRecipeIndex(position).selected){
            holder.itemLayout.setBackgroundResource(R.color.colorPrimary)
        }else{
            holder.itemLayout.setBackgroundColor(Color.WHITE)
        }
        holder.itemNombre.text = recipeList[position].name
    }

    override fun getItemCount(): Int {
        return  recipeList.size
    }

    fun setDataList(recipe: MutableList<Recipe>) {
        recipeList = recipe
    }

    fun getRecipeIndex(position: Int): Recipe {
        return recipeList[position]
    }
}