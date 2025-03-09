package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.usecases.SignInUseCase
import com.petproject.workflow.domain.usecases.VerifySuccessAuthorizationUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val verifySuccessAuthorizationUseCase: VerifySuccessAuthorizationUseCase
) : ViewModel() {

    private val _errorInputEmail = MutableLiveData<Boolean>(false)
    val errorInputEmail: LiveData<Boolean> get() = _errorInputEmail

    private val _errorInputPassword = MutableLiveData<Boolean>(false)
    val errorInputPassword: LiveData<Boolean> get() = _errorInputPassword

    private val _navigateToHomeScreen = MutableLiveData<String?>()
    val navigateToHomeScreen: LiveData<String?> get() = _navigateToHomeScreen

    var emailField: ObservableField<String> = ObservableField()
    var passwordField: ObservableField<String> = ObservableField()

    init {
        viewModelScope.launch {
            verifySuccessAuthorizationUseCase.invoke {
                _navigateToHomeScreen.value = it
            }
        }
    }

    fun signIn() {
        val email = emailField.get() ?: ""
        val password = passwordField.get() ?: ""
        if (validateInput(email, password)) {
            viewModelScope.launch {
                signInUseCase(email, password) {
                    _errorInputEmail.value = true
                    _errorInputPassword.value = true
                }
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