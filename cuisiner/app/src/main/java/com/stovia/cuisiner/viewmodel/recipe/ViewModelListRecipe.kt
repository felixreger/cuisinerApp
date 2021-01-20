package com.stovia.cuisiner.viewmodel.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.RecetaUseCase

class ViewModelListRecipe: ViewModel() {

    var listData = MutableLiveData<MutableList<String>>()
    private val recetaUseCase = RecetaUseCase()
    var rest = MutableLiveData<Boolean>()

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

    fun saveDataRecipe(recipeIndex: String) {
        recetaUseCase.saveRecipe(recipeIndex).observeForever{
            rest.value = it
        }
    }

    fun getRespuesta(): LiveData<Boolean>{
        return rest
    }

    fun setRecipeName(nombre: String) {
        recetaUseCase.setRecipeName(nombre)
    }

}