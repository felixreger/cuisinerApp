package com.stovia.cuisiner.ui.dialog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.common.base.Verify
import com.stovia.cuisiner.R
import kotlinx.android.synthetic.main.fragment_recipe_set_dialog.view.*


class RecipeSetFragmentDialog : DialogFragment() {
    // Use this instance of the interface to deliver action events
    internal lateinit var listener: NoticeDialogListener

    /* The activity that creates an instance of this dialog fragment must
             * implement this interface in order to receive event callbacks.
             * Each method passes the DialogFragment in case the host needs to query it. */
    interface NoticeDialogListener {
        fun onDialogSaveRecipe(dialog: DialogFragment,recipeName : String) : Boolean
        fun onDialogCancelRecipe(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = targetFragment as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement NoticeDialogListener"))
        }
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_recipe_set_dialog, container, false)

        rootView.saveButton.setOnClickListener{
            if(rootView.recipeNameEditText.text.isNotBlank()){
                if(listener.onDialogSaveRecipe(this,rootView.recipeNameEditText.text.toString())){
                    dismiss()
                }
                else{
                    Toast.makeText(context,"No puede haber dos recetas con el mismo nombre",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context,"Por favor, ingrese un nombre para la receta",Toast.LENGTH_SHORT).show()
            }
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}