package com.stovia.cuisiner.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

import com.stovia.cuisiner.ui.model.Product
import com.stovia.cuisiner.ui.model.UserData

class Repository {

    private val db = FirebaseFirestore.getInstance()
    private var firebaseAuth = FirebaseAuth.getInstance()

    private var nombreReceta : String?=null

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

    //todo unificar
    fun getIngredientProducts(email: String,nombre:String): LiveData<MutableList<Product>>{
        val mutableData = MutableLiveData<MutableList<Product>>()
        Log.d("PARAMETRO", nombre)
        db.collection("usuarios")
            .document(email)
            .collection("Nombre de la receta")
            .get().addOnSuccessListener { result  ->
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

    fun logInUser(email: String, password: String) : MutableLiveData<UserData> {
        val mutableUserData = MutableLiveData<UserData>() //esta bien dejarlo afuerA?
        firebaseAuth.signInWithEmailAndPassword(email,
            password).addOnCompleteListener {
            //Se notifica si la operacion ha sido satifactoria
            if(it.isSuccessful){
                mutableUserData.value = UserData("default","logged in")
            } else {
                mutableUserData.value = UserData("default","not logged in")
            }
        }
        return mutableUserData
    }

    fun signInUser(email: String, password: String) : MutableLiveData<UserData>{
        val mutableUserData = MutableLiveData<UserData>() //esta bien dejarlo afuerA?
        firebaseAuth.createUserWithEmailAndPassword(email,
            password).addOnCompleteListener {
            //Se notifica si la operacion ha sido satifactoria
            if (it.isSuccessful) {
                mutableUserData.value = UserData("default","signed in")
            } else {
                mutableUserData.value = UserData("default","not signed in")
            }
        }
        return mutableUserData
    }

    fun saveData(email: String, amount: String, unit: String, productName: String): LiveData<MutableList<Product>> {
        db.collection("usuarios") //Busco en usuarios
            .document(email ?: "") //Por mail
            .collection("productos") //Busco en productos
            .document(productName) //Por nombre de producto
            .set( //Se crea un documento por cada users y la clave es "email"
                hashMapOf(
                    "cantidad" to amount, //todo ver si se puede pasar numeric, definir en base a features
                    "unidad" to unit
                )
            )
        return getUserProducts(email)
    }

    fun updateData(email: String, amount: String, unit: String, productName: String): LiveData<MutableList<Product>> {
        db.collection("usuarios") //Busco en usuarios
                .document(email ?: "") //Por mail
                .collection("productos") //Busco en productos
                .document(productName) //Por nombre de producto
                .update(mapOf(
                    "cantidad" to amount,
                    "unidad" to unit
                ))
        return getUserProducts(email)
    }

    fun deleteProduct(email: String,productName: String): LiveData<MutableList<Product>> {
        db.collection("usuarios") //Busco en usuarios
                .document(email ?: "") //Por mail
                .collection("productos") //Busco en productos
                .document(productName).delete()
        return getUserProducts(email)
    }

    fun saveRecipe(product: String): LiveData<Boolean>{
        val mutableUserData = MutableLiveData<Boolean>()
            db.collection("usuarios") //Busco en usuarios
                .document("felixregert@gmail.com" ?: "") //Por mail
                .collection(nombreReceta!!) //Busco en productos
                .document(product) //Por nombre de producto
                .set( //Se crea un documento por cada users y la clave es "email"
                    hashMapOf(
                        "cantidad" to "10",
                        "unidad" to "kg"
                    )
                ).addOnCompleteListener {
                    mutableUserData.value = it.isSuccessful
                }
        return mutableUserData
    }

    fun getUserRecipe(email: String, tag: Boolean): LiveData<MutableList<String>>{
        val mutableData = MutableLiveData<MutableList<String>>()
        //Si tag es true, entonces me piden listar recetas
        //Si es false, me piden listar nombre de productos que tengo

        val recipe = mutableListOf<String>()
        if(tag){
            recipe.add("Pan dulce")
            recipe.add("Pan")
            recipe.add("Pan casero")
            recipe.add("Asado")
            recipe.add("Guiso")

        }else{
            recipe.add("Azucar")
            recipe.add("Agua")
            recipe.add("Sal")
            recipe.add("Manteca")
            recipe.add("Cafe")
            recipe.add("Pimienta")
            recipe.add("Azufre")
            recipe.add("Miel")
        }
        mutableData.value = recipe
        return mutableData
    }

    fun setRecipeName(nombre: String) {
        nombreReceta = nombre
    }


}