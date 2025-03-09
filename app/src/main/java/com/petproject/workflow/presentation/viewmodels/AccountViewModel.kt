package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {

    private val _navigateToLoginScreen = MutableLiveData<Boolean>(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _navigateToLoginScreen.value = true
        }
    }
}