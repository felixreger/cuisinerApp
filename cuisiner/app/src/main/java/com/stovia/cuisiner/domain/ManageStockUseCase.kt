package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class ManageStockUseCase {
    private val repository = Repository()

    fun saveUpdateProduct(email:String,
                          productName: String,
                          productAmount: String,
                          productUnit: String): LiveData<Boolean> {
        return repository.saveProduct(email,productAmount,productUnit,productName)
    }



    fun deleteProduct(email: String, productName: String): LiveData<Boolean> {
        return repository.deleteProduct(email,productName)
    }

    fun updateProduct(email: String, nombre: String, cant: String, un: String) {
        repository.saveProduct(email,nombre,cant,un)
    }
}