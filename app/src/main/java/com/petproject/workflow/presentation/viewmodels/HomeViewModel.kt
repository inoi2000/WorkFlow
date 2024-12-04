package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.petproject.workflow.data.repositories.EmployeeRepositoryImpl
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetEmployeeUseCase

class HomeViewModel(employeeId: String): ViewModel() {

    private val employeeRepository = EmployeeRepositoryImpl()
    private val getEmployeeUseCase = GetEmployeeUseCase(employeeRepository)

    val employee: LiveData<Employee> by lazy {
        getEmployeeUseCase(employeeId)
    }

}