package com.stovia.cuisiner.viewmodel.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.IngredientUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelIngredients: ViewModel() {

    var listData = MutableLiveData<MutableList<Product>>()
    private val listIngredientsUseCase = IngredientUseCase()


    private fun setIngredientsList(list : MutableList<Product>) {
        listData.value = list
    }

    fun getIngredientsList(email:String, nombreReceta:String) {
        //Quiero los ingredientes/productos con todos los detalles
        listIngredientsUseCase.getIngredientList(email, nombreReceta).observeForever {
            setIngredientsList(it)
        }
    }

    fun getIngredientsListLiveData(): LiveData<MutableList<Product>> {
        return listData
    }
}