package com.stovia.cuisiner.viewmodel.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.stovia.cuisiner.R

import com.stovia.cuisiner.ui.model.Product

class Adapter(private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    var productList = mutableListOf<Product>()

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener  {

        val  itemNombre: TextView = itemView.findViewById(R.id.nombreDeProductoTextView)
        val  itemCantidad: TextView = itemView.findViewById(R.id.cantidadTextView)
        val  itemUnidad: TextView = itemView.findViewById(R.id.UnidadDeCantidadTextVIew)
        val  relativeLayout: RelativeLayout = itemView.findViewById(R.id.itemViewLayout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                listener.onItemClick(position,relativeLayout)
            }
        }

        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            if(position != RecyclerView.NO_POSITION){
                  notifyDataSetChanged()
                listener.onLongClick(position,relativeLayout)
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
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemNombre.text = productList[position].nombre
        holder.itemCantidad.text = productList[position].cantidad
        holder.itemUnidad.text = productList[position].unidad
    }

    override fun getItemCount(): Int {
        return  productList.size
    }

    fun setDataList(user_products: MutableList<Product>) {
        productList = user_products
    }

    fun removeProduct(product: Product){
        productList.remove(product)
    }

    fun getProductIndex(position: Int): Product {
        return productList[position]
    }

}
