package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class ManageIngredientUseCase {
    private val repository = Repository()


    fun saveIngredient(recipeName: String,
                          ingredient: Product
                          ): LiveData<Boolean> {
        return repository.saveUserData("recipe",recipeName,"products",ingredient)
    }

//    fun updateIngredient(
//        email: String,
//        recipeName: String,
//        ingredientName: String,
//        ingredientAmount: String,
//        ingredientUnit: String
//    ): LiveData<MutableList<Product>> {
//        return repository.saveIngredient(email,recipeName,ingredientName,ingredientAmount,ingredientUnit)
//    }
}
