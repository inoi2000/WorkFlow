package com.petproject.workflow.presentation

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth

//    private val _employeeId = MutableLiveData<String?>()
//    val employeeId: LiveData<String?> get() = _employeeId

    private val _errorInputEmail = MutableLiveData<Boolean>(false)
    val errorInputEmail: LiveData<Boolean> get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>(false)
    val errorInputPassword: LiveData<Boolean> get() = _errorInputPassword


    private val _navigateToHomeScreen = MutableLiveData<String?>()
    val navigateToHomeScreen: LiveData<String?> get() = _navigateToHomeScreen



    var emailField: ObservableField<String> = ObservableField()
    var passwordField: ObservableField<String> = ObservableField()

    init {
        auth.addAuthStateListener { firebaseAuth: FirebaseAuth ->
            firebaseAuth.currentUser?.let {
                _navigateToHomeScreen.value = it.uid
            }
        }
    }

    fun signIn() {
        val email = emailField.get() ?: ""
        val password = passwordField.get() ?: ""
        if (validateInput(email, password)) {
            auth.signInWithEmailAndPassword(email, password)
                .addOnFailureListener {
                    _errorInputEmail.value = true
                    _errorInputPassword.value = true
                }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        var result = true
        if (email.isBlank()) {
            _errorInputEmail.value = true
            result = false
        }
        if (password.isBlank()) {
            _errorInputPassword.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputEmail() {
        _errorInputEmail.value = false
    }

    fun resetErrorInputPassword() {
        _errorInputPassword.value = false
    }


    fun onHomeScreenNavigated() {
        _navigateToHomeScreen.value = null
    }
}