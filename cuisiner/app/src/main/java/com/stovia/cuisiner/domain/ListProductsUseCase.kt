package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class ListProductsUseCase{

    private val repository = Repository()
    //todo parametros.
    fun getProductList(email:String) : LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        repository.getUserProducts(email).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

}