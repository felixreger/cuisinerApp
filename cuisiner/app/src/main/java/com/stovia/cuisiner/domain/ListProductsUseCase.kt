package com.stovia.cuisiner.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.Confirmation

class ListProductsUseCase{


    private val repository = Repository()

    //todo parametros.
    fun getProductList(email:String) : LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        repository.getUserProducts(email).observeForever {
            mutableData.value = it
        }
        return mutableData
    }
    /*
    fun deleteProducts(email: String, product: String): LiveData<Boolean> {
        val mutableUserData = MutableLiveData<Boolean>()
        repository.deleteProduct(email, product).observeForever {
            mutableUserData.value = it
        }
        return mutableUserData
    }*/

    fun deleteProductsConf(email: String, product: String): LiveData<Confirmation> {
        val mutableUserData = MutableLiveData<Confirmation>()
        repository.deleteProductRefresh(email, product).observeForever {
            mutableUserData.value = it
        }
        return mutableUserData
    }


}