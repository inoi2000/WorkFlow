package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Employee
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionEmployeeViewModel @Inject constructor(
    val requestManager: RequestManager
) : ViewModel() {

    private var allEmployees: List<Employee> = emptyList()

    private val _employeeList = MutableLiveData<List<Employee>>()
    val employeeList: LiveData<List<Employee>> get() = _employeeList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun loadData(
        loadingState: Boolean = true,
        getEmployee: suspend () -> List<Employee>
    ) {
        _loadingState.value = loadingState
        _errorState.value = null

        viewModelScope.launch {
            try {
                allEmployees = getEmployee()
                _employeeList.value = allEmployees
            } catch (e: Exception) {
                _errorState.value = "Ошибка загрузки сотрудников: ${e.localizedMessage}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun filterEmployees(pattern: String) {
        if (pattern.isBlank()) return
        val filteredList = allEmployees.filter { employee ->
            val searchPattern = pattern.lowercase().trim()
            employee.name.lowercase().contains(searchPattern)
        }
        _employeeList.value = filteredList
    }

    fun clearFilter() {
        _employeeList.value = allEmployees
    }
}