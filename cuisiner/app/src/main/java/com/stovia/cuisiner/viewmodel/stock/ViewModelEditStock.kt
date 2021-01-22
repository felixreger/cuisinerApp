package com.stovia.cuisiner.viewmodel.stock

import androidx.lifecycle.ViewModel
import com.stovia.cuisiner.domain.ManageStockUseCase
import java.util.*

class ViewModelEditStock : ViewModel(){
    private val manageStockUseCase = ManageStockUseCase()

    fun saveData(email:String,productName: String, productAmount: String, productUnit: String) {
        if(productName.isNotBlank() && productAmount.isNotBlank() && productUnit.isNotBlank()){
            manageStockUseCase.updateProduct(email,
                productName.toLowerCase(Locale.ROOT),
                productAmount.toLowerCase(Locale.ROOT),
                productUnit.toLowerCase(Locale.ROOT))
        }
    }
}