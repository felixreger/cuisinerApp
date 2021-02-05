package com.stovia.cuisiner.viewmodel.recipe

import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.R

import com.stovia.cuisiner.domain.RecipeUseCase
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.model.Recipe

class ViewModelRecipe :  ViewModel(){

    var listData = MutableLiveData<MutableList<Recipe>>()

    var selectedRecipeList = ArrayList<Recipe>()

    private val recipeUseCase = RecipeUseCase()

    fun getRecipeList(email:String) {
        //solamente quiero pedir los nombres de las recetas.
        recipeUseCase.getRecipeList(email).observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<Recipe>) {
        listData.value = list
    }


    //retorno una lista de nombres de recetas
    fun getRecipeListLiveData() : LiveData<MutableList<Recipe>> {
        return listData
    }

    fun selectRecipe(recipe: Recipe) {
        selectedRecipeList.add(recipe)
        recipe.selected = true
    }

    fun unselectRecipe(recipe: Recipe){
        selectedRecipeList.remove(recipe)
        recipe.selected = false
    }

    fun unselectRecipes() {
        for (recipe in selectedRecipeList){
            recipe.selected = false
        }
    }

}