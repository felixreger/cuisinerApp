package com.stovia.cuisiner.domain

import com.stovia.cuisiner.data.Repository

class ManageStockUseCase {
    private val repository = Repository()

    fun saveUpdateProduct(email:String,productName: String, productAmount: String, productUnit: String) {
        repository.saveData(email,productAmount,productUnit,productName)
    }
}