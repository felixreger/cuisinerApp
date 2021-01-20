package com.stovia.cuisiner.viewmodel.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.stovia.cuisiner.domain.RecetaUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelRecipe :  ViewModel(){

    var listData = MutableLiveData<MutableList<String>>() //Almacena los nombres de las recetas unicamente

    private val recetaUseCase = RecetaUseCase()

    fun getRecipeList(email:String, tag:Boolean) {
        recetaUseCase.getRecipeList(email,tag).observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<String>) {
        listData.value = list
    }

    fun getRecipeListLiveData() : LiveData<MutableList<String>> {
        return listData
    }


}