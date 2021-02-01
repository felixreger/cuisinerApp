package com.stovia.cuisiner.viewmodel.recipe

import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.domain.RecipeListUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelListRecipe: ViewModel() {

    var listData = MutableLiveData<MutableList<Product>>()
    private val recetaUseCase = RecipeListUseCase()
    var rest = MutableLiveData<Boolean>()

    private var recipeName = String()

    fun getProductosDisponibles(email:String) {
        //quiero todos los detalles de los productos asociados al email
        recetaUseCase.getProductosDisponibles(email).observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<Product>) {
        listData.value = list
    }

    fun getRecipeListLiveData() : LiveData<MutableList<Product>> {
        return listData
    }


    /*
    fun saveDataRecipe(recipeIndex: String) {
        recetaUseCase.saveRecipe(recipeIndex).observeForever{
            rest.value = it
        }
    }*/

    fun saveDataRecipe(email:String, product: Product) {
        recetaUseCase.saveRecipe(email, recipeName, product).observeForever{
            rest.value = it
        }
    }

    fun getRespuesta(): LiveData<Boolean>{
        return rest
    }

    fun setRecipeName(nombre: String) {
        recipeName = nombre
    }

//    fun getRecipesNames(email : String): LiveData<MutableList<String>> {
//        var repository = Repository()
//        val mutableData = MutableLiveData<MutableList<String>>()
//
//        repository.getUserRecipe(email).observeForever {
//            mutableData.value = it
//        }
//        return mutableData
//    }
}