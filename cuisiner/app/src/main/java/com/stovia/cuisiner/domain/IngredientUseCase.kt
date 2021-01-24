package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class IngredientUseCase {

    private val repository = Repository()

    fun getIngredientList(email:String,nombreReceta:String) : LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        //Quiero los ingredientes/productos con todos los detalles
        repository.getRecipeIngredients(email, nombreReceta).observeForever {
            mutableData.value = it
        }
        return mutableData
    }
}