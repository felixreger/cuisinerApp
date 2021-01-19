package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.viewmodel.ViewModelAddStock
import kotlinx.android.synthetic.main.fragment_add_stock.*


class AddStock : Fragment() {

    private val repo = Repository()
    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelAddStock::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = AddStockArgs.fromBundle(requireArguments()).email
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveProductButton.setOnClickListener{
            viewModel.saveData(email,ProductNameEditText.text.toString(),
                    AmountProductEditText.text.toString(),
                    UnitEditText.text.toString())
        }
    }


}