package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.RecipeSetFragmentDialog
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.model.Recipe
import com.stovia.cuisiner.viewmodel.adapter.AdapterAvailableProducts

import com.stovia.cuisiner.viewmodel.recipe.ViewModelListRecipe
import com.stovia.cuisiner.viewmodel.recipe.ViewModelRecipe

import kotlinx.android.synthetic.main.fragment_list_recipe.*

class AvailableProducts : Fragment(), AdapterAvailableProducts.OnItemClickListener,
    RecipeSetFragmentDialog.NoticeDialogListener {

    private lateinit var adapter: AdapterAvailableProducts
    private lateinit var email: String
    private val productos = mutableListOf<Product>()
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
        email = AvailableProductsArgs.fromBundle(requireArguments()).email
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

        adapter = AdapterAvailableProducts(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData()
        getSave()

        saveData.setOnClickListener {
            if(productos.isNotEmpty()){
                // TODO: 01/02/21 Esto de usar dos viewmodels es una crotada, hay que cambiarlo
                openSetRecipeNameDialog()
            }else
                Toast.makeText(context,"La receta no tiene ingredientes",Toast.LENGTH_SHORT).show()
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
            val newProduct = Product(i.nombre,"0",i.unidad)
            viewModel.saveDataRecipe(email,newProduct)
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

    override fun onItemClick(position: Int) {
        val product = adapter.getProductIndex(position)
        if(!product.selected){
            product.selected = true
            productos.add(product)
        }else {
            product.selected = false
            productos.remove(product)
        }
    }

    override fun onDialogSaveRecipe(dialog: DialogFragment, recipeName: String) : Boolean{
        val recipeNames = viewModelReceta.getRecipeListLiveData().value

        if(recipeNames != null)
            if(!recipeNames.contains(Recipe(recipeName))){
                saveDataRecipe(recipeName)
                Toast.makeText(context,"Receta guardada correctamente", Toast.LENGTH_SHORT).show()
                val action = AvailableProductsDirections.actionListProductsRecipeToReceta(email)
                findNavController().navigate(action)
                return true
            }else
                return false
        // TODO: 07/02/21 Controlar que venga con algo y avisarle al Dialog
        return true
    }

    override fun onDialogCancelRecipe(dialog: DialogFragment) {
        Toast.makeText(context,"Receta cancelada",Toast.LENGTH_SHORT).show()
    }

    private fun openSetRecipeNameDialog(){
        val dialogFragment = RecipeSetFragmentDialog()
        dialogFragment.setTargetFragment(this, 1)
        dialogFragment.show(requireFragmentManager(), "MyCustomDialog")
    }

}