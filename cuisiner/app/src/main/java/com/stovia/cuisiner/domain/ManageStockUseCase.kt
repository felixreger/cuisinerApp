package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class ManageStockUseCase {
    private val repository = Repository()

    fun saveUpdateProduct(email:String,
                          productName: String,
                          productAmount: String,
                          productUnit: String): LiveData<MutableList<Product>> {
        return repository.saveData(email,productAmount,productUnit,productName)
    }

    fun updateProduct(email:String,
                      productName: String,
                      productAmount: String,
                      productUnit: String): LiveData<MutableList<Product>> {
        return repository.updateData(email,productAmount,productUnit,productName)
    }

    fun deleteProduct(email: String, productName: String): LiveData<MutableList<Product>> {
        return repository.deleteProduct(email,productName)
    }
}