package com.stovia.cuisiner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ListProductsUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelMainActivity : ViewModel(){
    private val listProductsUseCase = ListProductsUseCase()
    var listData = MutableLiveData<List<Product>>()

    fun getProductList() {
        listProductsUseCase.getProductList().observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : List<Product>) {
        listData.value = list
    }

    fun getProductListLiveData() : LiveData<List<Product>> {
        return listData
    }
}