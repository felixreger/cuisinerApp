package com.stovia.cuisiner.viewmodel.recipe

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageIngredientUseCase
import com.stovia.cuisiner.ui.model.Product
import java.util.*

class ViewModelEditIngredient : ViewModel() {
    private val manageIngredientUseCase = ManageIngredientUseCase()

    fun saveData(
        recipeName: String,
        ingredient: Product
    ) {
        manageIngredientUseCase.saveIngredient(recipeName,ingredient)
    }
}
