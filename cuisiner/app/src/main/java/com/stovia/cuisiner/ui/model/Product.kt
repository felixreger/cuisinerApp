package com.stovia.cuisiner.ui.model
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.RelativeLayout
import android.widget.Toast
import com.stovia.cuisiner.viewmodel.adapter.Adapter

class Product (val nombre: String? = "nombre",
               var cantidad: String? = "cantidad",
               val unidad: String? = "unidad") : Adapter.OnItemClickListener, Parcelable {

    var selected : Boolean = false

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombre)
        parcel.writeString(cantidad)
        parcel.writeString(unidad)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Producto(nombre=$nombre, cantidad=$cantidad, unidad=$unidad)"
    }

    override fun onItemClick(position: Int) {

    }

    override fun onLongClick(position: Int) {

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Product

        if (nombre != other.nombre) return false
        if (cantidad != other.cantidad) return false
        if (unidad != other.unidad) return false
        if (selected != other.selected) return false
//todo, no tiene que comparar el selected al momento de ver si contiene la lista al producto del que estamos hablando


        return true
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }

}


