package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class RecipeUseCase {

    private val repository = Repository()

    fun getRecipeList(email: String): LiveData<MutableList<String>> {
        val mutableData = MutableLiveData<MutableList<String>>()
        //solamente quiero pedir los nombres de las recetas.
        //todo configurar el email + nombre receta
        repository.getUserRecipe(email).observeForever {
                mutableData.value = it
            }
            return mutableData
    }

}