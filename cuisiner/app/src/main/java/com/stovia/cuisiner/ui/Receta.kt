package com.stovia.cuisiner.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.stock.ListaStockDirections
import com.stovia.cuisiner.viewmodel.ViewModelList
import com.stovia.cuisiner.viewmodel.adapter.Adapter
import kotlinx.android.synthetic.main.fragment_lista_stock.*


class Receta : Fragment() , Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var email: String

    private val listOfProducts = mutableListOf<Product>()

    private val viewModel by lazy {
        ViewModelProviders.of(this).get(ViewModelList::class.java) }
    //se vuelve el de la lsita

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        this.email = RecetaArgs.fromBundle(requireArguments()).email
        Log.d("Valor recibido: ", email as String)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_receta, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = Adapter(requireContext(),this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData(email)

        addProductButton.setOnClickListener {
            val action = ListaStockDirections.actionListaStockToAddStock()
//            val action = ListaStockDirections.actionListaStockToAddStock("felipe@gmail.com")
            findNavController().navigate(action)
        }

        saveProductButton.setOnClickListener {
            val repo = Repository()
//            repo.addAll(listOfProducts, "felipe@gmail.com")
        }
    }

    private fun observeData(email : String){
//        viewModel.fetchUserData(email).observe(viewLifecycleOwner, Observer{
//            adapter.setDataList(it)
//            adapter.notifyDataSetChanged()
//        })
    }

    override fun onItemClick(position: Int) {
        //Obtener los datos que esta tocando el usuario
        val producto = adapter.productList[position]
        val nombre = producto.nombre
        listOfProducts.add(producto)
        Toast.makeText(context, "Agregado el producto $nombre", Toast.LENGTH_SHORT).show()
    }
}