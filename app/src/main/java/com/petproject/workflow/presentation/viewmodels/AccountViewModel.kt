package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AccountViewModel: ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth

    private val _navigateToLoginScreen = MutableLiveData<Boolean>(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    fun signOut() {
        auth.signOut()
        _navigateToLoginScreen.value = true
    }


}