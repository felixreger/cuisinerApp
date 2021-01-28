package com.stovia.cuisiner.ui.dialog

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.recipe.Ingredients
import com.stovia.cuisiner.viewmodel.recipe.ViewModelEditIngredient
import kotlinx.android.synthetic.main.fragment_edit_ingredient_dialog.view.*
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.amountEditText
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.cancelButton
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.recipeName
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.saveButton
import kotlinx.android.synthetic.main.fragment_edit_stock_dialog.view.unitEditText
import java.util.*

class EditIngredientDialogFragment : DialogFragment() {

    private lateinit var email: String
    private lateinit var recipeName: String

    private lateinit var ingredient : Parcelable
    private lateinit var ingredientName : String
    private lateinit var ingredientAmount : String
    private lateinit var ingredientUnit : String

    private val viewModelEditIngredient = ViewModelEditIngredient()
//    private val viewModelDeleteIngredient = ViewModelDeleteIngredient()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?    ): View? {

        val rootView: View = inflater.inflate(R.layout.fragment_edit_ingredient_dialog, container, false)

        getParam()
        Toast.makeText(context, ingredientName, Toast.LENGTH_SHORT).show()
        initEditText(rootView)


        rootView.saveButton.setOnClickListener {

            //actualizar el ingrediente
                // editar el parcelable
                //construir un nuevo objecto
            ingredient = Product(
                rootView.ingredientNameText.text.toString(),
                rootView.amountEditText.text.toString(),
                rootView.unitEditText.text.toString()
            )

            viewModelEditIngredient.saveData(
                email,
                recipeName,
                ingredient as Product //todo falta actualizar
            )
            dismiss()
        }

        rootView.cancelButton.setOnClickListener {
            dismiss()
        }
        return rootView
    }

    private fun getParam() {
        ingredient  = requireArguments().getParcelable("ingredient")!!
        email = requireArguments().getString("email").toString()
        recipeName = requireArguments().getString("recipeName").toString()

        ingredientName = (ingredient as Product).nombre.toString()
        ingredientAmount = (ingredient as Product).cantidad.toString()
        ingredientUnit = (ingredient as Product).unidad.toString()
    }

    private fun initEditText(rootView: View) {

        rootView.ingredientNameText.text = ingredientName
        rootView.amountEditText.setText(ingredientAmount)
        rootView.unitEditText.setText(ingredientUnit)
        rootView.recipeName.text = recipeName
    }
}