package com.stovia.cuisiner.viewmodel.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.stovia.cuisiner.domain.RecipeUseCase

class ViewModelRecipe :  ViewModel(){

    var listData = MutableLiveData<MutableList<String>>()

    private val recipeUseCase = RecipeUseCase()

    fun getRecipeList(email:String) {
        //solamente quiero pedir los nombres de las recetas.
        recipeUseCase.getRecipeList(email).observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<String>) {
        listData.value = list
    }


    //retorno una lista de nombres de recetas
    fun getRecipeListLiveData() : LiveData<MutableList<String>> {
        return listData
    }


}