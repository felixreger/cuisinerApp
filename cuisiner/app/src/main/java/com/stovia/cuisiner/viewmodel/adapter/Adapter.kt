package com.stovia.cuisiner.viewmodel.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.RecyclerView
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product

class Adapter(private val context: Context, private val listener: OnItemClickListener): RecyclerView.Adapter<Adapter.ViewHolder>() {

    var productList = mutableListOf<Product>()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener  {

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
            Toast.makeText(context,"Clicked ${position}",Toast.LENGTH_SHORT).show()
            if(position != RecyclerView.NO_POSITION){
                notifyDataSetChanged()
                listener.onItemClick(position)
            }
        }



        override fun onLongClick(v: View?): Boolean {
            val position = adapterPosition
            Toast.makeText(context,"Clicked ${position}",Toast.LENGTH_SHORT).show()
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
        val view = LayoutInflater.from(context).inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(getProductIndex(position).selected){
            holder.relativeLayout.setBackgroundResource(R.color.colorPrimary)
        }else{
            holder.relativeLayout.setBackgroundColor(Color.WHITE)
        }
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

    fun removeProduct(product: String){
        removeByName(product)
    }

    private fun removeByName(product: String) {
        for (i in productList){
            if(i.nombre == product){
                productList.remove(i)
                return
            }
        }
    }

    fun getProductIndex(position: Int): Product {
        return productList[position]
    }

    fun addProduct(name: String, amount: String, unit: String) {
        productList.add(Product(name, amount, unit))
    }


}
