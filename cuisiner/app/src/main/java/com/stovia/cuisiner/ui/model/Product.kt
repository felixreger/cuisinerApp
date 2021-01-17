package com.stovia.cuisiner.ui.model

import android.os.Parcel
import android.os.Parcelable

import com.stovia.cuisiner.viewmodel.adapter.Adapter

class Product (val nombre: String? = "nombre",
               var cantidad: String? = "cantidad",
               val unidad: String? = "unidad") : Adapter.OnItemClickListener, Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()) {
    }

    override fun onItemClick(position: Int) {
        //HACER LO QUE QUIERA
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

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}