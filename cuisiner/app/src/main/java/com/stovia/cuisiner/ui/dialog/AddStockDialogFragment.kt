package com.stovia.cuisiner.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.viewmodel.stock.ViewModelAddStock
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.*
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.view.*


class AddStockDialogFragment : DialogFragment() {

    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelAddStock::class.java) }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_add_stock_dialog, container, false)

        email = requireArguments().getString("email").toString()

        rootView.saveButton.setOnClickListener{
            viewModel.saveData(email,
                nameEditText.text.toString(),
                amountEditText.text.toString(),
                unitEditText.text.toString())
            dismiss()
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}