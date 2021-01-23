package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.EditStockDialogFragment
import com.stovia.cuisiner.viewmodel.stock.ViewModelEditStock
import com.stovia.cuisiner.viewmodel.stock.ViewModelList
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
//            val action = ListaStockDirections.actionListaStockToAddStock(email)
//            findNavController().navigate(action)
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

    override fun onItemClick(position: Int) {
        val product = adapter.getProductIndex(position)
        Toast.makeText(context, "Click on ${product.nombre}", Toast.LENGTH_SHORT).show()
//        val action2 = ListaStockDirections.actionListaStockToEditStock(product, this.email)
//        findNavController().navigate(action2)
        val dialogFragment = EditStockDialogFragment()

        val args = Bundle()
        args.putString("email",email)
        args.putStringArrayList("productData", arrayListOf(
            product.nombre,
            product.cantidad,
            product.unidad
        ))
        dialogFragment.arguments = args
//        Toast.makeText(context, args.toString(), Toast.LENGTH_SHORT).show()

        fragmentManager?.let { it1 -> dialogFragment.show(it1, "custom dialog") }
    }
}