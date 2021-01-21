package com.stovia.cuisiner.ui.dialog

//Dialog fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.stovia.cuisiner.R
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.*


class EditStockDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_edit_stock_dialog, container, false)

        initEditText(rootView)

        rootView.saveButton.setOnClickListener {
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