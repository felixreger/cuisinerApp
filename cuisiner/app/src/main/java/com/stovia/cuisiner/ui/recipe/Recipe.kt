package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.viewmodel.recipe.ViewModelRecipe
import com.stovia.cuisiner.viewmodel.adapter.AdapterRecipe
import kotlinx.android.synthetic.main.fragment_recipe.*

class Recipe : Fragment() , AdapterRecipe.OnItemClickListener {

    private lateinit var adapter: AdapterRecipe
    private lateinit var email: String

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelRecipe::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = RecipeArgs.fromBundle(requireArguments()).email
        //solamente quiero pedir los nombres de las recetas.
        viewModel.getRecipeList(email)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterRecipe(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData()

        addRecipe.setOnClickListener {
            val action = RecipeDirections.actionRecetaToListProductsRecipe(email)
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        viewModel.getRecipeListLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int, relativeLayout: RelativeLayout) {
        if (!viewModel.isContextualModeEnabled){
            val action = RecipeDirections.actionRecetaToIngredients(email, adapter.getRecipeIndex(position))
            findNavController().navigate(action)
        }else{
            val recipe = adapter.getRecipeIndex(position)
            if(viewModel.selectedRecipeList.contains(recipe)){
                viewModel.unselectRecipe(recipe,relativeLayout)
                if (viewModel.selectedRecipeList.isEmpty()){
                    viewModel.isContextualModeEnabled = false
                }
            }
            else{
                viewModel.selectRecipe(recipe,relativeLayout)

            }
        }
    }

    override fun onLongClick(position: Int, relativeLayout: RelativeLayout) {
        val recipe = adapter.getRecipeIndex(position)

        if(!viewModel.isContextualModeEnabled){
            viewModel.selectRecipe(recipe,relativeLayout)
            viewModel.isContextualModeEnabled = true
        }
        else{
            if(viewModel.selectedRecipeList.contains(recipe)){
                viewModel.unselectRecipe(recipe,relativeLayout)
                if (viewModel.selectedRecipeList.isEmpty()){
                    viewModel.isContextualModeEnabled = false
                }
            }
            else{
                viewModel.selectRecipe(recipe,relativeLayout)
            }
        }
    }


//    override fun onItemClick(position: Int, relativeLayout: RelativeLayout) {
//        val product = adapter.getProductIndex(position)
//
//        if (!viewModel.isContextualModeEnabled){
//            if(!productos.contains(position)){
//                Toast.makeText(context,"Agregado ", Toast.LENGTH_SHORT).show()
//                //adapter.changeItem(position)
//                productos.add(position)
//            }else {
//                Toast.makeText(context, "DES Agregado ", Toast.LENGTH_SHORT).show()
//                //adapter.changeItem(position)
//                productos.remove(position)
//            }
//        }
//        else{
//            if(viewModel.isProductSelected(product)){
//                viewModel.unselectProduct(product,relativeLayout)
//            }
//            else{
//                viewModel.selectProduct(product,relativeLayout)
//                if (viewModel.selectedProductList.isEmpty()){
//                    viewModel.isContextualModeEnabled = false
//                }
//            }
//        }
//    }
//
//    override fun onLongClick(position: Int, relativeLayout: RelativeLayout) {
//        val product = adapter.getProductIndex(position)
//
//        if(!viewModel.isContextualModeEnabled){
//            viewModel.selectProduct(product,relativeLayout)
//        }
//        else{
//            if(viewModel.isProductSelected(product)){
//                viewModel.unselectProduct(product,relativeLayout)
//            }
//            else{
//                viewModel.selectProduct(product,relativeLayout)
//                if (viewModel.selectedProductList.isEmpty()){
//                    viewModel.isContextualModeEnabled = false
//                }
//            }
//        }
//    }
}