package com.stovia.cuisiner.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController

import com.google.firebase.analytics.FirebaseAnalytics

import com.stovia.cuisiner.R
import com.stovia.cuisiner.ui.model.UserData
import com.stovia.cuisiner.viewmodel.ViewModelLogin
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    var loginViewModel = ViewModelLogin()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.loginViewModel = ViewModelProviders.of(this).get(ViewModelLogin::class.java)



        initSignInButton()
        initLogInButton()

//        analytics
        val analytics = FirebaseAnalytics.getInstance(requireContext())
        val bundle = Bundle()
        bundle.putString("message", "Integracion de Firebase completa")
        analytics.logEvent("InitScreen", bundle) //No anda

//        start
        emailEditText.setText("felipe@gmail.com")
        passwordEditText.setText("felipe")
    }

    private fun initLogInButton() {

        //todo ver si lo metemos en el vm

        val userDataObserver = Observer<UserData>{
            if (it.status == "logged in"){
                Toast.makeText(context,"Logueado correctamente", Toast.LENGTH_SHORT).show()
                showHome(emailEditText.text.toString())
            }
            else{
                if (it.status =="not logged in"){
                    Toast.makeText(context,"Datos incorrectos", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginViewModel.completeLogin().observe(viewLifecycleOwner,userDataObserver)

        logInButton.setOnClickListener{
            loginViewModel.logIn(emailEditText.text.toString(),
                passwordEditText.text.toString())
        }
    }

    private fun initSignInButton() {

        val userDataObserver = Observer<UserData>{
            if (it.status == "signed in"){
                Toast.makeText(context,"Registro completado", Toast.LENGTH_SHORT).show()
                showHome(emailEditText.text.toString())
            }
            else{
                if (it.status == "not signed in"){
                    Toast.makeText(context,"Registro fallido", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loginViewModel.completeSignIn().observe(viewLifecycleOwner,userDataObserver)

        singUpButton.setOnClickListener {
            loginViewModel.signUp(emailEditText.text.toString(),
                    passwordEditText.text.toString())
        }

    }

    private fun showHome(email: String) {
        val action = LoginFragmentDirections.actionLoginFragmentToMainFragment(emailEditText.text.toString())
        findNavController().navigate(action)
    }
}