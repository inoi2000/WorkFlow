package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Access
import com.petproject.workflow.domain.usecases.GetAllCurrentAccessesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccessListViewModel @Inject constructor(
    private val getAllCurrentAccessesUseCase: GetAllCurrentAccessesUseCase
): ViewModel() {

    private val _accessList = MutableLiveData<List<Access>>()
    val accessList: LiveData<List<Access>> get() = _accessList

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
                val accesses = getAllCurrentAccessesUseCase()
                _accessList.value = accesses
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