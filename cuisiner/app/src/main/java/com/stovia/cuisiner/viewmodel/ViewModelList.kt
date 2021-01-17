package com.stovia.cuisiner.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.stovia.cuisiner.domain.ListProductsUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelList :  ViewModel(){

    private val listProductsUseCase = ListProductsUseCase()
    var listData = MutableLiveData<MutableList<Product>>()

    fun getProductList() {
        listProductsUseCase.getProductList().observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<Product>) {
        listData.value = list
    }

    fun getProductListLiveData() : LiveData<MutableList<Product>> {
        return listData
    }

}