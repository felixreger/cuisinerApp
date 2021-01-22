package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    //se vuelve el de la lista

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = RecipeArgs.fromBundle(requireArguments()).email
        viewModel.getRecipeList(email, true)
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

    override fun onItemClick(position: Int) {
        //Toast.makeText(context,"Click ", Toast.LENGTH_SHORT).show()
        //Si aprieto en pan dulce, se carga esa pantalla.
        val action = RecipeDirections.actionRecetaToIngredients(email, adapter.getRecipeIndex(position))
        findNavController().navigate(action)
    }
}