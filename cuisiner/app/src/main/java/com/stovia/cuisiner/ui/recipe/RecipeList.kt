package com.stovia.cuisiner.ui.recipe

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Recipe
import com.stovia.cuisiner.viewmodel.recipe.ViewModelRecipe
import com.stovia.cuisiner.viewmodel.adapter.AdapterRecipe
import kotlinx.android.synthetic.main.fragment_recipe.*

class RecipeList : Fragment() , AdapterRecipe.OnItemClickListener {

    private lateinit var adapter: AdapterRecipe
    private lateinit var email: String

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelRecipe::class.java)
    }

    private var actionMode : ActionMode? = null

    var isContextualModeEnabled : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = RecipeListArgs.fromBundle(requireArguments()).email
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
            val action = RecipeListDirections.actionRecetaToListProductsRecipe(email)
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
        val recipe = adapter.getRecipeIndex(position)

        if (!isContextualModeEnabled){
            val action = RecipeListDirections.actionRecetaToIngredients(email, recipe.name!!)
            findNavController().navigate(action)
        }else{
            if(recipe.selected){
                viewModel.unselectRecipe(recipe)
                if (viewModel.selectedRecipeList.isEmpty()){
                    isContextualModeEnabled = false
                    actionMode?.finish()
                }
            }
            else{
                viewModel.selectRecipe(recipe)
            }
        }
    }

    override fun onLongClick(position: Int) {
        val recipe = adapter.getRecipeIndex(position)

        if(!isContextualModeEnabled){
            viewModel.selectedRecipeList = ArrayList<Recipe>()
            actionMode = requireActivity().startActionMode(actionModeCallback)
            isContextualModeEnabled = true
            viewModel.selectRecipe(recipe)
        }
        else{
            if(recipe.selected){
                viewModel.unselectRecipe(recipe)
                if (viewModel.selectedRecipeList.isEmpty()){
                    isContextualModeEnabled = false
                    actionMode?.finish()
                }
            }
            else{
                viewModel.selectRecipe(recipe)
            }
        }
        Log.d("seleccionados", viewModel.selectedRecipeList.toString())
    }


    private val actionModeCallback = object : ActionMode.Callback {

        // Called when the action mode is created; startActionMode() was called
        override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
            // Inflate a menu resource providing context menu items
            val inflater: MenuInflater = mode.menuInflater
            inflater.inflate(R.menu.context_menu, menu)
            return true
        }

        // Called each time the action mode is shown. Always called after onCreateActionMode, but
        // may be called multiple times if the mode is invalidated.
        override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
            return false // Return false if nothing is done
        }

        // Called when the user selects a contextual menu item
        override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.menu_delete -> {
                    Toast.makeText(context, "Delete no implementado", Toast.LENGTH_SHORT).show()
                    // TODO: 26/01/21 Borrar.exe
    //                viewModel.deleteProducts(email)
                    mode.finish() // Action picked, so close the CAB(J)
                    true
                }
                else -> false
            }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            viewModel.unselectRecipes()
            adapter.notifyDataSetChanged()
            isContextualModeEnabled = false
            actionMode = null
        }
    }
}