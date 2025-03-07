package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.data.repositories.AuthorizationRepositoryImpl
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.launch

class AccountViewModel: ViewModel() {

    private val repository: AuthorizationRepository = AuthorizationRepositoryImpl()
    private val signOutUseCase = SignOutUseCase(repository)

    private val _navigateToLoginScreen = MutableLiveData<Boolean>(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _navigateToLoginScreen.value = true
        }
    }
}