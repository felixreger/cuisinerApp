package com.stovia.cuisiner.domain

import androidx.lifecycle.MutableLiveData
import com.stovia.cuisiner.data.Repository

import com.stovia.cuisiner.ui.model.UserData

class LoginRegisterUseCase {
    private val repository = Repository()
    private val mutableUserData = MutableLiveData<UserData>()

    fun logInUser(email: String, password: String) : MutableLiveData<UserData> {

        repository.logInUser(email,password).observeForever { //todo ver video de gaston de por que se usa observeForever
            mutableUserData.value = it
        }
        return mutableUserData
    }

    fun signInUser(email: String, password: String) : MutableLiveData<UserData>{

        repository.signInUser(email,password).observeForever { //todo ver video de gaston de por que se usa observeForever
            mutableUserData.value = it
        }
        return mutableUserData
    }
}