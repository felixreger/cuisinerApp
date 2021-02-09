package com.stovia.cuisiner.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.Confirmation

class ManageStockUseCase {
    private val repository = Repository()
    /*
    fun saveUpdateProduct(email:String,
                          productName: String,
                          productAmount: String,
                          productUnit: String): LiveData<Boolean> {
        return repository.saveProduct(email,productAmount,productUnit,productName)
    } */

    fun saveUpdateProductConfirmation(email:String,
                          productName: String,
                          productAmount: String,
                          productUnit: String): LiveData<Product> {

        val mutableUserData = MutableLiveData<Product>()
        repository.saveProductConfirmation(email,productAmount,productUnit,productName).observeForever {
            mutableUserData.value = it
        }
        return mutableUserData
    }

    fun deleteProduct(email: String, productName: String): LiveData<Boolean> {
        return repository.deleteProduct(email,productName)
    }

    fun updateProduct(email: String, nombre: String, cant: String, un: String) {
        repository.saveProduct(email,nombre,cant,un)
    }
}