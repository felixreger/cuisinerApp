package com.stovia.cuisiner.viewmodel.recipe

import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.R

import com.stovia.cuisiner.domain.RecipeUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelRecipe :  ViewModel(){

    var listData = MutableLiveData<MutableList<String>>()

    var selectedRecipeList = ArrayList<String>()
    var isContextualModeEnabled : Boolean = false

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

    fun selectRecipe(recipe: String) {
        selectedRecipeList.add(recipe)
    }

    fun unselectRecipe(recipe: String){
        selectedRecipeList.remove(recipe)
    }

}