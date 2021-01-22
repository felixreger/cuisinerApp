package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.viewmodel.adapter.AdapterRecipe

import com.stovia.cuisiner.viewmodel.recipe.ViewModelListRecipe

import kotlinx.android.synthetic.main.fragment_list_recipe.*

class ListRecipe : Fragment(), AdapterRecipe.OnItemClickListener {

    private lateinit var adapter: AdapterRecipe
    private lateinit var email: String
    private val productos = mutableListOf<Int>()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelListRecipe::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = ListRecipeArgs.fromBundle(requireArguments()).email
        viewModel.getRecipeList(email, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AdapterRecipe(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData()
        getSave()


        saveData.setOnClickListener {
            recipeName.visibility = View.VISIBLE
            saveName.visibility = View.VISIBLE
            

            saveName.setOnClickListener{
                val nombre = recipeName.text.toString()
                saveDataRecipe(nombre)
            }

        }
    }

    private fun saveDataRecipe(nombre:String){
        viewModel.setRecipeName(nombre)

        for (i in productos){
            viewModel.saveDataRecipe(adapter.getRecipeIndex(i))
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

        if(!productos.contains(position)){
            Toast.makeText(context,"Agregado ", Toast.LENGTH_SHORT).show()
            //adapter.changeItem(position)
            productos.add(position)
        }else {
            Toast.makeText(context, "DES Agregado ", Toast.LENGTH_SHORT).show()
            //adapter.changeItem(position)
            productos.remove(position)
        }
    }
}