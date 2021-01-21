package com.stovia.cuisiner.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.stovia.cuisiner.R
import kotlinx.android.synthetic.main.fragment_recipe_set_dialog.view.*


class RecipeSetFragmentDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_recipe_set_dialog, container, false)

        rootView.saveButton.setOnClickListener{
            dismiss()
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}