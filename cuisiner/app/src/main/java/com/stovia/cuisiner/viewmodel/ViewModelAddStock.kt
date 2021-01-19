package com.stovia.cuisiner.viewmodel

import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageStockUseCase

class ViewModelAddStock : ViewModel(){

    private val manageStockUseCase = ManageStockUseCase()

    fun saveData(email:String,productName: String, productAmount: String, productUnit: String) {
        if(productName.isNotBlank() && productAmount.isNotBlank() && productUnit.isNotBlank()){
            manageStockUseCase.saveUpdateProduct(email,productName,productAmount,productUnit)
        }
    }
}