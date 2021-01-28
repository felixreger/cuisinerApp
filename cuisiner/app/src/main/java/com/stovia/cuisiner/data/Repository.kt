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

    //devuelve los productos de un usuario con nombre, cantidad y unidad
    fun getUserProducts(email: String): LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        db.collection("usuarios")
            .document(email)
            .collection("productos")
            .get().addOnSuccessListener { result ->
                val listData = mutableListOf<Product>()
                for (i in result) {
                    val amount = i.getString("cantidad")
                    val unit = i.getString("unidad")
                    val userAux = Product(i.id, amount!!, unit!!)
                    listData.add(userAux)
                }
                mutableData.value = listData
            }
        return mutableData
    }

    // devuelve lista con los nombres de las recetas de un usuario, buscando por la clave "email"
    fun getUserRecipe(email: String): LiveData<MutableList<String>>{
        val mutableData = MutableLiveData<MutableList<String>>()
        db.collection("receta")
            .whereEqualTo("email", email)
            .get().addOnSuccessListener { result ->
                val listData = mutableListOf<String>()
                for (i in result) {
                    val nameRecipe = i.getString("name")
                    listData.add(nameRecipe!!)
                }
                mutableData.value = listData
            }
        return mutableData
    }

    fun saveRecipe(email: String, nombre:String, product: Product): LiveData<Boolean>{
        val mutableUserData = MutableLiveData<Boolean>()
        val clave = nombre.replace("\\s".toRegex(), "")
        db.collection("receta") //Busco en usuarios
            .document("$email#$clave" ?: "") //Por mail
            .also {  it.set(hashMapOf("email" to email, "name" to nombre)) }
            .collection("productos") //Busco en productos
            .document(product.nombre!!) //Por nombre de producto
            .set( //Se crea un documento por cada users y la clave es "email"
                hashMapOf("cantidad" to product.cantidad, "unidad" to product.unidad)
            ).addOnCompleteListener {
                mutableUserData.value = it.isSuccessful
            }
        return mutableUserData
    }

    //con el mail y el nombre de la recetas me devuelve los ingredientes con cantidad y unidad
    fun getRecipeIngredients(email: String, nombreReceta: String): LiveData<MutableList<Product>> {
        val mutableData = MutableLiveData<MutableList<Product>>()
        val clave = nombreReceta.replace("\\s".toRegex(), "")

        db.collection("receta") //usuario
            .also { it.whereEqualTo("name", nombreReceta)}
            .document("$email#$clave") // email
            .collection("productos") // productos
            .get().addOnSuccessListener { result ->
                val listData = mutableListOf<Product>()
                for (i in result) {
                    val amount = i.getString("cantidad")
                    val unit = i.getString("unidad")
                    val userAux = Product(i.id, amount!!, unit!!)
                    listData.add(userAux)
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

    fun saveProduct(email: String, amount: String, unit: String, productName: String): LiveData<Boolean>{
        val mutableUserData = MutableLiveData<Boolean>()

        db.collection("usuarios") //Busco en usuarios
            .document(email ?: "") //Por mail
            .collection("productos") //Busco en productos
            .document(productName) //Por nombre de producto
            .set( //Se crea un documento por cada users y la clave es "email"
                hashMapOf(
                    "cantidad" to amount, //todo ver si se puede pasar numeric, definir en base a features
                    "unidad" to unit
                )
            ).addOnCompleteListener {
                mutableUserData.value  = it.isSuccessful
        }
        return mutableUserData
    }

    /*
    fun updateData(email: String, amount: String, unit: String, productName: String){

    }*/

    fun deleteProduct(email: String, productName: String): LiveData<Boolean> {
        val mutableUserData = MutableLiveData<Boolean>()

        db.collection("usuarios") //Busco en usuarios
            .document(email ?: "") //Por mail
            .collection("productos") //Busco en productos
            .document(productName).delete().addOnCompleteListener {
                mutableUserData.value= it.isSuccessful
            }
        return mutableUserData
    }
}