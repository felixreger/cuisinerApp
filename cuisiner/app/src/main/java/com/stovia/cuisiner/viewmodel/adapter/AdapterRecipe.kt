package com.stovia.cuisiner.viewmodel.adapter

import android.content.Context
import android.content.ReceiverCallNotAllowedException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.recipe.Recipe

class AdapterRecipe(private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<AdapterRecipe.ViewHolder>() {

    var recipeList = mutableListOf<String>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

        val  itemNombre: TextView = itemView.findViewById(R.id.nombreReceta)

        val itemViewRelativeLayout : RelativeLayout = itemView.findViewById(R.id.itemRelativeLayout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position,itemViewRelativeLayout)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onLongClick(position,itemViewRelativeLayout)
                return true
            }
            return false
        }
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int,relativeLayout: RelativeLayout)
        fun onLongClick(position: Int,relativeLayout: RelativeLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_view_recipe, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNombre.text = recipeList[position]
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
}