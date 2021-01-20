package com.stovia.cuisiner.viewmodel

import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageStockUseCase
import java.util.*

class ViewModelDeleteStock : ViewModel(){
    private val manageStockUseCase = ManageStockUseCase()

    fun deleteProduct(email:String,productName: String) {
        if(productName.isNotBlank()){
            manageStockUseCase.deleteProduct(email,productName.toLowerCase(Locale.ROOT))
        }
    }
}