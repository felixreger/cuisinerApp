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
import com.stovia.cuisiner.ui.dialog.EditIngredientDialogFragment
import com.stovia.cuisiner.ui.dialog.EditStockDialogFragment
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.adapter.Adapter
import com.stovia.cuisiner.viewmodel.recipe.ViewModelIngredients
import kotlinx.android.synthetic.main.fragment_list_recipe.*

class Ingredients : Fragment(), Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var email: String
    private lateinit var nombreReceta: String

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelIngredients::class.java)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = IngredientsArgs.fromBundle(requireArguments()).email
        nombreReceta = IngredientsArgs.fromBundle(requireArguments()).nombreReceta
        viewModel.getIngredientsList(email,nombreReceta)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ingredients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter(requireContext(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        observeData()

    }

    private fun observeData() {
        viewModel.getIngredientsListLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int) {
//        Toast.makeText(context,"Click ${xd.nombre}", Toast.LENGTH_SHORT).show()
        val ingredient = adapter.getProductIndex(position)

        val dialogFragment = EditIngredientDialogFragment()

        val args = Bundle()
        args.putString("email",email)
        args.putString("recipeName",nombreReceta)


        //todo ver si puedo pasarlo asi: ingredient as Product
        args.putParcelable("ingredient",ingredient)

        dialogFragment.arguments = args
//        Toast.makeText(context, args.toString(), Toast.LENGTH_SHORT).show()

        dialogFragment.show(requireFragmentManager(), "ad")
//        fragmentManager?.let { it1 -> dialogFragment.show(it1, "custom dialog") }
    }

}
