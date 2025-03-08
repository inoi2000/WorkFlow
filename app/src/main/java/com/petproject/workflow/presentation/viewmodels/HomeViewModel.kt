package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.data.network.exceptions.AuthException
import com.petproject.workflow.data.repositories.AuthorizationRepositoryImpl
import com.petproject.workflow.data.repositories.EmployeeRepositoryImpl
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetEmployeeUseCase
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class HomeViewModel(employeeId: String): ViewModel() {

    private val employeeRepository = EmployeeRepositoryImpl()
    private val getEmployeeUseCase = GetEmployeeUseCase(employeeRepository)
    private val authorizationRepository = AuthorizationRepositoryImpl()
    private val signOutUseCase = SignOutUseCase(authorizationRepository)

    private val _navigateToLoginScreen = MutableLiveData(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee

    init {
        viewModelScope.launch(CoroutineExceptionHandler { _, throwable ->
            if (throwable is AuthException) {
                runBlocking {
                    signOutUseCase()
                }
                _navigateToLoginScreen.value = true
            }
        }) {
            _employee.value = getEmployeeUseCase(employeeId)
        }
    }

}