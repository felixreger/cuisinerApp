package com.stovia.cuisiner.viewmodel.stock

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

    fun selectProduct(product: Product, relativeLayout: RelativeLayout) {
        relativeLayout.setBackgroundColor(R.attr.colorPrimary)
        selectedProductList.add(product)
        product.selected = true
    }

    fun unselectProduct(product: Product, relativeLayout: RelativeLayout){
        relativeLayout.setBackgroundColor(Color.hashCode())
        product.selected = false
        selectedProductList.remove(product)
    }

}