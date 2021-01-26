package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.EditStockDialogFragment
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.ViewModelEditStock
import com.stovia.cuisiner.viewmodel.stock.ViewModelList
import com.stovia.cuisiner.viewmodel.adapter.Adapter
import kotlinx.android.synthetic.main.fragment_lista_stock.*


class ListaStock : Fragment(), Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelList::class.java) }

    var isContextualModeEnabled : Boolean = false
    //normal --> selection

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        email = ListaStockArgs.fromBundle(requireArguments()).email
        viewModel.getProductList(email)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_stock, container, false)
    }//todo resolver

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Adapter(requireContext(), this) //todo ver si meter el adapter en el vm
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        observeData()

        addProductButton.setOnClickListener {
            val dialogFragment = AddStockDialogFragment()
            val args = Bundle()
            args.putString("email",email)
            dialogFragment.arguments = args
            fragmentManager?.let { it1 -> dialogFragment.show(it1, "custom dialog") }
        }
    }

    private fun observeData() {
        viewModel.getProductListLiveData().observe(viewLifecycleOwner, Observer {
            adapter.setDataList(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onLongClick(position: Int, relativeLayout: RelativeLayout) {
        val product = adapter.getProductIndex(position)

        if(viewModel.selectedProductList.isEmpty()){
            isContextualModeEnabled = true
            viewModel.selectProduct(product,relativeLayout)
        }
        else{
            if (!product.selected){ //si el producto no esta agregado lo agrega
                viewModel.selectProduct(product,relativeLayout)
            }
            else{ //si el producto esta agregado lo saca
                viewModel.unselectProduct(product,relativeLayout)
                if (viewModel.selectedProductList.isEmpty()){
                    isContextualModeEnabled = false
                }
            }
        }
        Log.d("seleccionados", viewModel.selectedProductList.toString())

    }

    override fun onItemClick(position: Int, relativeLayout: RelativeLayout) {
        val product = adapter.getProductIndex(position)

        //solamente si esta vacia la lista funciona el edit
        if(!isContextualModeEnabled){
            editProduct(product)
        }
        else{
            if(!product.selected){
                viewModel.selectProduct(product,relativeLayout)
            }
            else{
                viewModel.unselectProduct(product,relativeLayout)
                if (viewModel.selectedProductList.isEmpty()){
                    isContextualModeEnabled = false
                }
            }
            Log.d("seleccionados", viewModel.selectedProductList.toString())
        }
    }

    private fun editProduct(product: Product){
        val dialogFragment = EditStockDialogFragment()
        val args = Bundle()
        args.putString("email",email)
        args.putStringArrayList("productData", arrayListOf(
            product.nombre,
            product.cantidad,
            product.unidad
        ))
        dialogFragment.arguments = args
        fragmentManager?.let { it1 -> dialogFragment.show(it1, "custom dialog") }
    }


}