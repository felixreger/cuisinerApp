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
import com.stovia.cuisiner.ui.model.Product

class AdapterList(private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<AdapterList.ViewHolder>() {

    var productList = mutableListOf<Product>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val itemNombre: TextView = itemView.findViewById(R.id.nombreReceta)
        val itemLayout : RelativeLayout = itemView.findViewById(R.id.itemRelativeLayout)

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
        if(getProductIndex(position).selected){
            holder.itemLayout.setBackgroundResource(R.color.colorPrimary)
//            holder.itemNombre.setTextColor(Color.WHITE)
//            holder.itemCantidad.setTextColor(Color.WHITE)
//            holder.itemUnidad.setTextColor(Color.WHITE)
        }else{
            holder.itemLayout.setBackgroundColor(Color.TRANSPARENT)
//            Falta ponerle el color viejo, no se como obtenerlo desde R
        }
        holder.itemNombre.text = productList[position].nombre
    }

    override fun getItemCount(): Int {
        return  productList.size
    }

    fun setDataList(user_products: MutableList<Product>) {
        productList = user_products
    }

    fun getProductIndex(position: Int): Product {
        return productList[position]
    }

}