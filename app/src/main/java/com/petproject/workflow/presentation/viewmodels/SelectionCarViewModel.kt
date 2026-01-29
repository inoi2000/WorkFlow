package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.usecases.GetAllCarsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionCarViewModel @Inject constructor(
    private val getAllCarsUseCase: GetAllCarsUseCase
) : ViewModel() {

    private val _carList = MutableLiveData<List<Car>>()
    val carList: LiveData<List<Car>> get() = _carList

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
                val car = getAllCarsUseCase()
                _carList.value = car
            } catch (e: Exception) {
                _errorState.value = "Ошибка загрузки машин"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun refreshData() {
        loadData()
    }
}