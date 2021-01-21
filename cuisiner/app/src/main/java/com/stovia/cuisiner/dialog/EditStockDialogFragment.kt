package com.stovia.cuisiner.dialog

//Dialog fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.stovia.cuisiner.R


class EditStockDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_edit_stock_dialog,container,false)
        return rootView
    }
}