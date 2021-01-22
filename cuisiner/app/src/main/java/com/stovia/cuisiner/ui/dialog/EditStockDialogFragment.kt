package com.stovia.cuisiner.ui.dialog

//Dialog fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.ViewModelDeleteStock
import com.stovia.cuisiner.viewmodel.stock.ViewModelEditStock
import kotlinx.android.synthetic.main.fragment_edit_stock.*
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.*
import java.util.*


class EditStockDialogFragment : DialogFragment() {

    private lateinit var email: String
    private val viewModelEditStock = ViewModelEditStock()
    private val viewModelDeleteStock = ViewModelDeleteStock()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_edit_stock_dialog, container, false)
        email = requireArguments().getString("email").toString()
        initEditText(rootView)

        rootView.saveButton.setOnClickListener {
            viewModelEditStock.saveData(email,
                rootView.dialogHeader.text.toString().toLowerCase(Locale.ROOT),
                rootView.unitEditText.text.toString().toLowerCase(Locale.ROOT),
                rootView.amountEditText.text.toString().toLowerCase(Locale.ROOT))
            dismiss()
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    private fun initEditText(rootView: View) {

        val productData = requireArguments().getStringArrayList("productData")

        rootView.dialogHeader.text = productData?.get(0).toString()
        rootView.unitEditText.setText(productData?.get(1).toString())
        rootView.amountEditText.setText(productData?.get(2).toString())
    }
}