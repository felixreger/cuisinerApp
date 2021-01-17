package com.stovia.cuisiner.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.model.UserData

class Repository {


    private val db = FirebaseFirestore.getInstance()
    private var firebaseAuth = FirebaseAuth.getInstance()

    fun getUserProducts(email: String): LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        db.collection("usuarios")
            .document(email)
            .collection("productos")
            .get().addOnSuccessListener { result ->
                val listData = mutableListOf<Product>()
                for (document in result) {
                    val cantidad = document.getString("cantidad")
                    val unidad = document.getString("unidad")
                    val usuario = Product(document.id, cantidad!!, unidad!!)
                    listData.add(usuario)
                }
                mutableData.value = listData
            }
        return mutableData
    }

    fun logInUser(email: String, password: String): MutableLiveData<UserData> {
        val mutableUserData = MutableLiveData<UserData>()
        firebaseAuth.signInWithEmailAndPassword(email,
            password).addOnCompleteListener {
            //Se notifica si la operacion ha sido satifactoria
            if(it.isSuccessful){
                mutableUserData.value = UserData("MENEM",true)
            } else {
                mutableUserData.value = UserData("MENEMn't",false)
            }
        }
        return mutableUserData
    }

    fun signInUser(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email,
            password).addOnCompleteListener {
            //Se notifica si la operacion ha sido satifactoria
            if (it.isSuccessful) {

            } else {

            }
        }
    }

    fun saveData(email: String, cantidad: String, unidad: String, nombre: String){
        db.collection("usuarios") //Busco en usuarios
            .document(email ?: "") //Por mail
            .collection("productos") //Busco en productos
            .document(nombre) //Por nombre de producto
            .set( //Se crea un documento por cada users y la clave es "email"
                hashMapOf(
                    "cantidad" to cantidad, //todo ver si se puede pasar numeric, definir en base a features
                    "unidad" to unidad
                )
            )
    }
}