package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetAllEmployeesByQueryUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val query: String,
    private val getAllEmployeesByQueryUseCase: GetAllEmployeesByQueryUseCase,
    val requestManager: RequestManager
) : ViewModel() {

    private val _queryText = MutableLiveData(query)
    val queryText: LiveData<String> = _queryText

    private val _employeeList = MutableLiveData(emptyList<Employee>())
    val employeeList: LiveData<List<Employee>> = _employeeList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    init {
        loadData()
    }

    fun updateQuery(query: String) {
        _queryText.value = query
    }

    fun loadData() {
        _loadingState.value = true
        _errorState.value = null

        viewModelScope.launch {
            try {
                val employees = getAllEmployeesByQueryUseCase(_queryText.value ?: "")
                _employeeList.value = employees
                _loadingState.value = false
            } catch (e: Exception) {
                _errorState.value = "Не удалось загрузить данные"
                _loadingState.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}