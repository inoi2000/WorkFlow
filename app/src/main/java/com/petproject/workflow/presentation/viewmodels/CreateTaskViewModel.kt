package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetAllEmployeesForAssignTaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTaskViewModel @Inject constructor(
    private val getAllEmployeesForAssignTaskUseCase: GetAllEmployeesForAssignTaskUseCase
): ViewModel() {

    private val _employeeList = MutableLiveData<List<Employee>>()
    val employeeList: LiveData<List<Employee>> get() = _employeeList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _employeeList.value = getAllEmployeesForAssignTaskUseCase()
        }
    }
}