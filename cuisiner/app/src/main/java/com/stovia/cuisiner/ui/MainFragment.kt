package com.stovia.cuisiner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.ViewModelMainActivity
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private var viewModel = ViewModelMainActivity()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val email = MainFragmentArgs.fromBundle(requireArguments()).email
        Toast.makeText(context,email, Toast.LENGTH_SHORT).show()

        viewModel = ViewModelProviders.of(this).get(ViewModelMainActivity::class.java)
        val productListObserver = Observer<List<Product>>(){
            Log.d("Product", it.toString())
        }

        viewModel.getProductListLiveData().observe(viewLifecycleOwner,productListObserver)

        getProductListButton.setOnClickListener {
            viewModel.getProductList()
        }

        goToRecipeListButton.setOnClickListener{
            Toast.makeText(context, "Lista de recetas No implementado", Toast.LENGTH_SHORT).show()
        }

        goToProductListButton.setOnClickListener {
            Toast.makeText(context, "Lista de productos No implementado", Toast.LENGTH_SHORT).show()
        }
    }

}