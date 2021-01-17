package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import kotlinx.android.synthetic.main.fragment_add_stock.*
import java.util.*


class AddStock : Fragment() {

    private val repo = Repository()
    private lateinit var email: String

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
            repo.saveData(email, AmountProductEditText.text.toString().toLowerCase(Locale.ROOT), UnitEditText.text.toString().toLowerCase(),
                ProductNameEditText.text.toString().toLowerCase(Locale.ROOT))
        }
    }


}