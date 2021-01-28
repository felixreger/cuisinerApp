package com.stovia.cuisiner.viewmodel.recipe

import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageIngredientUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelEditIngredient : ViewModel() {
    private val manageIngredientUseCase = ManageIngredientUseCase()

    fun saveData(
        email: String,
        recipeName: String,
        ingredient: Product
    ) {
        manageIngredientUseCase.saveIngredient(email,recipeName,ingredient)
    }
}
