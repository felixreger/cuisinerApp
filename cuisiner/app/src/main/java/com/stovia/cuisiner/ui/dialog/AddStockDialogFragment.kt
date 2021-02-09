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
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.ViewModelAddStock
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.*
import kotlinx.android.synthetic.main.fragment_add_stock_dialog.view.*


class AddStockDialogFragment : DialogFragment() {

    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelAddStock::class.java) }

    internal lateinit var listener: DialogListener

    private lateinit var savedProduct : Product

    interface DialogListener{
        fun checkContainsName(product: Product) : Boolean
        fun methodToPassMyData(name:String, amount:String, unit:String)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as DialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement DialogListener"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_add_stock_dialog, container, false)

        email = requireArguments().getString("email").toString()

        rootView.saveButton.setOnClickListener{
            savedProduct = Product(nameEditText.text.toString(),amountEditText.text.toString(),unitEditText.text.toString())
            if (!listener.checkContainsName(savedProduct)){
                viewModel.saveData(email,
                    nameEditText.text.toString(),
                    amountEditText.text.toString(),
                    unitEditText.text.toString())
                Toast.makeText(context,"Producto guardado correctamnete",Toast.LENGTH_SHORT).show()
                dismiss()
            }else{
                Toast.makeText(context,"No puede haber dos proudctos con el mismo nombre",Toast.LENGTH_SHORT).show()
            }
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
            listener.methodToPassMyData(it.nombre!!, it.cantidad!!, it.unidad!!)
        })
    }
}