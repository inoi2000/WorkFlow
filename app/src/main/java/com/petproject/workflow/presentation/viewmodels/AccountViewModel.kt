package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetCurrentEmployeeUseCase
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase
) : ViewModel() {

    private val _employee = MutableLiveData<Employee>()
    public val employee: LiveData<Employee> get() = _employee

    private val _navigateToLoginScreen = MutableLiveData(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    init {
        viewModelScope.launch {
            _employee.value = getCurrentEmployeeUseCase.invoke()
        }
    }

    fun signOut() {
        viewModelScope.launch {
            signOutUseCase()
            _navigateToLoginScreen.value = true
        }
    }
}