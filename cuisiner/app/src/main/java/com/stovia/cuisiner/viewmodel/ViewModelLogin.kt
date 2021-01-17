package com.stovia.cuisiner.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.stovia.cuisiner.domain.LoginRegisterUseCase
import com.stovia.cuisiner.ui.model.UserData


class ViewModelLogin :  ViewModel(){
    var logInSignUpUseCase = LoginRegisterUseCase()
    private var userLiveData = MutableLiveData<UserData>()

    fun logIn(email: String, password: String) {
//        if (email.isNotEmpty() && password.isNotEmpty()){ //todo
        logInSignUpUseCase.logInUser(email,password).observeForever{
            userLiveData.value = it
        }
//        }
    }

    fun signUp(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()){
            logInSignUpUseCase.signInUser(email,password)
        }
    }

    fun completeLogin(): MutableLiveData<UserData> {
        return userLiveData
    }
}