package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.data.repositories.EmployeeRepositoryImpl
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetEmployeeUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeViewModel(employeeId: String): ViewModel() {

    private val employeeRepository = EmployeeRepositoryImpl()
    private val getEmployeeUseCase = GetEmployeeUseCase(employeeRepository)

    private val _employee = MutableLiveData<Employee>()
    val employee: LiveData<Employee> = _employee

    init {
        viewModelScope.launch {
            _employee.value = getEmployeeUseCase(employeeId)
        }
    }

}