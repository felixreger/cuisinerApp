package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class IngredientUseCase {

    private val repository = Repository()
    //todo parametros.
    fun getIngredientList(email:String,nombre:String) : LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        repository.getIngredientProducts(email,nombre).observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}