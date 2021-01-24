package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class RecipeListUseCase {
    private val repository = Repository()

    //este metodo ingresa a la coleccion original
    fun getProductosDisponibles(email:String) : LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        //quiero todos los detalles de los productos del usuario felix
        repository.getUserProducts(email).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun saveRecipe(email:String, recipeName:String, product: Product): LiveData<Boolean>{
        val mutableUserData = MutableLiveData<Boolean>()
        repository.saveRecipe(email, recipeName, product).observeForever {
            mutableUserData.value = it
        }
        return mutableUserData
    }

}