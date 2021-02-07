package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.compose.ui.graphics.Color.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.dialog.AddStockDialogFragment
import com.stovia.cuisiner.ui.dialog.EditStockDialogFragment
import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.viewmodel.stock.ViewModelList
import com.stovia.cuisiner.viewmodel.adapter.Adapter
import kotlinx.android.synthetic.main.fragment_lista_stock.*


class ListaStock : Fragment(), Adapter.OnItemClickListener {

    private lateinit var adapter: Adapter
    private lateinit var email: String

    private val viewModel by lazy { ViewModelProviders.of(this).get(ViewModelList::class.java) }

    var isContextualModeEnabled : Boolean = false

    private var actionMode : ActionMode? = null

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

    override fun onItemClick(position: Int) {
        val product = adapter.getProductIndex(position)

        //solamente si esta vacia la lista funciona el edit
        if(!isContextualModeEnabled){
            editProduct(product)
        }
        else{
            if(!product.selected){
                viewModel.selectProduct(product)
            }
            else{
                viewModel.unselectProduct(product)
                if (viewModel.selectedProductList.isEmpty()){
                    isContextualModeEnabled = false
                    actionMode?.finish()
                }
            }
        }
    }

    override fun onLongClick(position: Int) {
        val product = adapter.getProductIndex(position)

        if(!isContextualModeEnabled){
            viewModel.selectedProductList = ArrayList<Product>()
            actionMode = requireActivity().startActionMode(actionModeCallback)
            isContextualModeEnabled = true
            viewModel.selectProduct(product)
        }
        else{
            if (!product.selected){ //si el producto no esta agregado lo agrega
                viewModel.selectProduct(product)
            }
            else{ //si el producto esta agregado lo saca
                viewModel.unselectProduct(product)
                if (viewModel.selectedProductList.isEmpty()){
                    isContextualModeEnabled = false
                    actionMode?.finish()
                }
            }
        }
        Log.d("seleccionados", viewModel.selectedProductList.toString())
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
                    Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
                    // TODO: 26/01/21 Borrar.exe
                    viewModel.deleteProducts(email)
                    mode.finish() // Action picked, so close the CAB
                    true
                }
                else -> false
                }
        }

        // Called when the user exits the action mode
        override fun onDestroyActionMode(mode: ActionMode) {
            viewModel.unselectProducts()
            adapter.notifyDataSetChanged()
            isContextualModeEnabled = false
            actionMode = null
        }
    }


}