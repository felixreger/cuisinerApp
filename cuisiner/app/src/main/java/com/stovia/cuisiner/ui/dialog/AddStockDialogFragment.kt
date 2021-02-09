package com.stovia.cuisiner.ui.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stovia.cuisiner.R
import com.stovia.cuisiner.viewmodel.stock.ViewModelAddStock
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.*
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.view.*


class AddStockDialogFragment : DialogFragment() {

    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelAddStock::class.java) }

    interface MyContract{
        fun methodToPassMyData(name:String, amount:String, unit:String)
    }

    var mHost : MyContract?=null

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_add_stock_dialog, container, false)

        email = requireArguments().getString("email").toString()

        rootView.saveButton.setOnClickListener{
            val name = nameEditText.text.toString()
            val amount = amountEditText.text.toString()
            val unit = unitEditText.text.toString()

            viewModel.saveData(email, name, amount, unit)
            dismiss()
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
    }

    private fun observeData() {
        viewModel.getResultSaveData().observe(requireActivity(), Observer {
            mHost!!.methodToPassMyData(it.nombre!!, it.cantidad!!, it.unidad!!)
        })
    }

    override fun onAttach(context: Context) {
        try {
            mHost = targetFragment as MyContract
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement NoticeDialogListener"))
        }
        super.onAttach(context)
    }
}