package com.stovia.cuisiner.viewmodel.stock

import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.widget.RelativeLayout
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.R

import com.stovia.cuisiner.domain.ListProductsUseCase
import com.stovia.cuisiner.ui.model.Product

class ViewModelList :  ViewModel(){

    private val listProductsUseCase = ListProductsUseCase()
    var listData = MutableLiveData<MutableList<Product>>()

    var selectedProductList = ArrayList<Product>()

    var result = MutableLiveData<Boolean>()

    fun getProductList(email:String) {
        listProductsUseCase.getProductList(email).observeForever {
            setProductList(it)
        }
    }

    private fun setProductList(list : MutableList<Product>) {
        listData.value = list
    }

    fun getProductListLiveData() : LiveData<MutableList<Product>> {
        return listData
    }

    fun selectProduct(product: Product) {
        selectedProductList.add(product)
        product.selected = true
    }

    fun unselectProduct(product: Product){
        selectedProductList.remove(product)
        product.selected = false
    }

    private fun shareCurrentItem() {
        TODO("Not yet implemented")
    }

    fun deleteProducts(email:String) {

        for(i in selectedProductList){
            listProductsUseCase.deleteProducts(email, i.nombre!!).observeForever {
                result.value = it
            }
        }

    }

    fun unselectProducts() {
        for (product in selectedProductList){
           product.selected = false
        }
    }


}