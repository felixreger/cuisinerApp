package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.RecipeSetFragmentDialog
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.adapter.AdapterList

import com.stovia.cuisiner.viewmodel.recipe.ViewModelListRecipe
import com.stovia.cuisiner.viewmodel.recipe.ViewModelRecipe

import kotlinx.android.synthetic.main.fragment_list_recipe.*

class ListRecipe : Fragment(), AdapterList.OnItemClickListener,
    RecipeSetFragmentDialog.NoticeDialogListener {

    private lateinit var adapter: AdapterList
    private lateinit var email: String
    private val productos = mutableListOf<Int>()
    private var newRecipeName : String = "default"
    private val recipeNameAsigned : Boolean = false

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelListRecipe::class.java)
    }

    private val viewModelReceta by lazy {
        ViewModelProviders.of(this).get(ViewModelRecipe::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = ListRecipeArgs.fromBundle(requireArguments()).email
        //quiero todos los detalles de los productos asociados al email
        viewModel.getProductosDisponibles(email)
        viewModelReceta.getRecipeList(email)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterList(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData()
        getSave()

        saveData.setOnClickListener {
            //Si todavia no se seteo el nombre, abre el pop up para setearl
            if(!recipeNameAsigned){
                val dialogFragment = RecipeSetFragmentDialog()
                dialogFragment.setTargetFragment(this, 1);
                dialogFragment.show(requireFragmentManager(), "MyCustomDialog");
            }else{
                if(productos.isNotEmpty()){
                    val recipeNames = viewModelReceta.getRecipeListLiveData().value
                    // TODO: 01/02/21 Esto de usar dos viewmodels es una crotada, hay que cambiarlo
                    if (recipeNames != null) {
                        if(!recipeNames.contains(newRecipeName)){
                            saveDataRecipe(newRecipeName)
                            Toast.makeText(context,"Receta guardada correctamente", Toast.LENGTH_SHORT).show()
                            val action = ListRecipeDirections.actionListProductsRecipeToReceta(email)
                            findNavController().navigate(action)
                        }else{
                            Toast.makeText(context,"No puede haber dos recetas con el mismo nombre",Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    Toast.makeText(context,"La receta no tiene ingredientes",Toast.LENGTH_SHORT).show()
                }
            }
        }

        addProducts.setOnClickListener{
            val dialogFragment = AddStockDialogFragment()
            val args = Bundle()
            args.putString("email",email)
            dialogFragment.arguments = args
            fragmentManager?.let { it1 -> dialogFragment.show(it1, "custom dialog") }

        }

    }

    private fun saveDataRecipe(nombre:String){
        viewModel.setRecipeName(nombre)

        for (i in productos){
            viewModel.saveDataRecipe(email, Product(adapter.getProductIndex(i).nombre,"0", adapter.getProductIndex(i).unidad))
        }

    }

    private fun getSave() {
        viewModel.getRespuesta().observe(viewLifecycleOwner, Observer {
            if (it){
                Toast.makeText(context,"Info guardada ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Info NO guardada ", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun observeData() {
        viewModel.getRecipeListLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int,relativeLayout: RelativeLayout) {
        if(!productos.contains(position)){
            //adapter.changeItem(position)
            relativeLayout.setBackgroundColor(R.attr.colorPrimary)
            productos.add(position)
        }else {
            //adapter.changeItem(position)
            relativeLayout.setBackgroundColor(Color.hashCode())
            productos.remove(position)
        }
    }

    override fun onDialogSaveRecipe(dialog: DialogFragment, recipeName: String) {
        newRecipeName = recipeName
    }

    override fun onDialogCancelRecipe(dialog: DialogFragment) {
        Toast.makeText(context,"Receta cancelada",Toast.LENGTH_SHORT).show()
    }

}