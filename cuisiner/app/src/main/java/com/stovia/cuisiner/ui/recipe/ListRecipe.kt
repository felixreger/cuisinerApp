package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.LoginFragmentDirections
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.RecipeSetFragmentDialog
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.adapter.AdapterList
import com.stovia.cuisiner.viewmodel.adapter.AdapterRecipe

import com.stovia.cuisiner.viewmodel.recipe.ViewModelListRecipe

import kotlinx.android.synthetic.main.fragment_list_recipe.*
import java.util.*

class ListRecipe : Fragment(), AdapterList.OnItemClickListener,
    RecipeSetFragmentDialog.NoticeDialogListener {

    private lateinit var adapter: AdapterList
    private lateinit var email: String
    private val productos = mutableListOf<Int>()
    private var newRecipeName : String = "default"

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelListRecipe::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = ListRecipeArgs.fromBundle(requireArguments()).email
        //quiero todos los detalles de los productos asociados al email
        //todo ya esta hecho, pero no tengo el control
        viewModel.getProductosDisponibles(email)
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
            if(newRecipeName == "default"){
                val dialogFragment = RecipeSetFragmentDialog()
                dialogFragment.setTargetFragment(this, 1);
                dialogFragment.show(requireFragmentManager(), "MyCustomDialog");
            }else{
                saveDataRecipe(newRecipeName)
                Toast.makeText(context,"Receta guardada correctamente", Toast.LENGTH_SHORT).show()
                val action = ListRecipeDirections.actionListProductsRecipeToReceta(email)
                findNavController().navigate(action)
            }
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