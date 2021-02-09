package com.stovia.cuisiner.viewmodel.stock

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageStockUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelAddStock : ViewModel(){

    private val manageStockUseCase = ManageStockUseCase()

    var result = MutableLiveData<Product>()

/*
    fun saveData(email:String,productName: String, productAmount: String, productUnit: String) {
        if(productName.isNotBlank() && productAmount.isNotBlank() && productUnit.isNotBlank()){
            manageStockUseCase.saveUpdateProduct(email,productName,productAmount,productUnit)
        }
    }*/

    fun saveData(email:String,productName: String, productAmount: String, productUnit: String) {
        if(productName.isNotBlank() && productAmount.isNotBlank() && productUnit.isNotBlank()){
            manageStockUseCase.saveUpdateProductConfirmation(email,productName,productAmount,productUnit).observeForever {
                result.value = it
            }
        }
    }

    fun getResultSaveData(): LiveData<Product>{
        return result
    }

}