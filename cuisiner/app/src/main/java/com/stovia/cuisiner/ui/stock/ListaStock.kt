package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.stovia.cuisiner.R
import com.stovia.cuisiner.viewmodel.ViewModelList
import com.stovia.cuisiner.viewmodel.adapter.Adapter

import kotlinx.android.synthetic.main.fragment_lista_stock.*


class ListaStock : Fragment(), Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelList::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = ListaStockArgs.fromBundle(requireArguments()).email
        viewModel.getProductList(email)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter(requireContext(), this) //todo ver si meter el adapter en el vm
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData(email)

        addProductButton.setOnClickListener {
            val action = ListaStockDirections.actionListaStockToAddStock(email)
            findNavController().navigate(action)
        }
    }

    private fun observeData(email : String){
        viewModel.getProductListLiveData().observe(viewLifecycleOwner, Observer{
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onItemClick(position: Int) {
        val producto = adapter.getProductIndex(position)
        //val action2 = ListaStockDirections.actionListaStockToEditStock("felipe@gmail.com", producto)
        //findNavController().navigate(action2)
        Toast.makeText(context, "Click on $position", Toast.LENGTH_SHORT).show()
    }
}