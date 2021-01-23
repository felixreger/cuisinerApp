package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository


class RecetaUseCase {

    private val repository = Repository()
    //todo parametros.
    fun getRecipeList(email:String, tag:Boolean) : LiveData<MutableList<String>> {
        val mutableData = MutableLiveData<MutableList<String>>()
        repository.getUserRecipe(email,tag).observeForever {
            mutableData.value = it
        }
        return mutableData
    }

    fun saveRecipe(s: String): LiveData<Boolean>{
        val mutableUserData = MutableLiveData<Boolean>()
//        repository.saveRecipe(s).observeForever {
//            mutableUserData.value = it
//        }
        return mutableUserData
    }

    fun setRecipeName(nombre: String) {
        repository.setRecipeName(nombre)
    }

}