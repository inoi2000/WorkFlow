package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetAllEmployeesForAssignTaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateTaskSelectionEmployeeViewModel @Inject constructor(
    private val getAllEmployeesForAssignTaskUseCase: GetAllEmployeesForAssignTaskUseCase,
    val requestManager: RequestManager
) : ViewModel() {

    private val _employeeList = MutableLiveData<List<Employee>>()
    val employeeList: LiveData<List<Employee>> get() = _employeeList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    init {
        loadData()
    }

    fun loadData() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                val employees = getAllEmployeesForAssignTaskUseCase()
                _employeeList.value = employees
            } catch (e: Exception) {
                _errorState.value = "Ошибка загрузки сотрудников"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}