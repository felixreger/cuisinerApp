package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_edit_stock.*

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.ViewModelDeleteStock
import com.stovia.cuisiner.viewmodel.stock.ViewModelEditStock
import java.util.*




class EditStock : Fragment() {

    private lateinit var email: String
    private lateinit var product: Product
    private val viewModelEditStock = ViewModelEditStock()
    private val viewModelDeleteStock = ViewModelDeleteStock()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.product = EditStockArgs.fromBundle(requireArguments()).product
        this.email = EditStockArgs.fromBundle(requireArguments()).email
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()

        guardarButton.setOnClickListener {
            viewModelEditStock.saveData(email,
                    editTextTextProductName.text.toString().toLowerCase(Locale.ROOT),
                    editTextTextAmountName.text.toString().toLowerCase(Locale.ROOT),
                    editTextTextUnitName.text.toString().toLowerCase(Locale.ROOT))
        }

        borrarButton.setOnClickListener {
            viewModelDeleteStock.deleteProduct(email,editTextTextProductName.text.toString().toLowerCase(Locale.ROOT))
        }
    }

    private fun initFields() {
        editTextTextProductName.setText(product.nombre.toString())
        editTextTextAmountName.setText(product.cantidad.toString())
        editTextTextUnitName.setText(product.unidad.toString())
    }

}