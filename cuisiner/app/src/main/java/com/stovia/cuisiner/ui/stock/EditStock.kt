package com.stovia.cuisiner.ui.stock

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import kotlinx.android.synthetic.main.fragment_add_stock.*
import kotlinx.android.synthetic.main.fragment_edit_stock.*
import kotlinx.android.synthetic.main.item_view.*

import com.stovia.cuisiner.R
import com.stovia.cuisiner.data.Repository
import com.stovia.cuisiner.ui.model.Product
import java.util.*


class EditStock : Fragment() {

    private val repo = Repository()
    private lateinit var email: String
    private lateinit var producto: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.producto = EditStockArgs.fromBundle(requireArguments()).product
        this.email = EditStockArgs.fromBundle(requireArguments()).email
        Toast.makeText(context, this.producto.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(context,this.email, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_stock, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        guardarButton.setOnClickListener {
            /*
            repo.saveData(email,
                AmountProductEditText.text.toString().toLowerCase(Locale.ROOT),
                UnitEditText.text.toString().toLowerCase(),
                nombreDeProductoTextView.text.toString().toLowerCase(Locale.ROOT))*/

        }

        borrarButton.setOnClickListener {
        }
    }

}