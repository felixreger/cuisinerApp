package com.stovia.cuisiner.domain

import androidx.lifecycle.LiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product

class ManageIngredientUseCase {
    private val repository = Repository()


    fun saveIngredient(
        email: String,
        recipeName: String,
        ingredient: Product
    ): LiveData<Boolean> {
        return repository.saveRecipe(email,recipeName,ingredient)
    }

}
