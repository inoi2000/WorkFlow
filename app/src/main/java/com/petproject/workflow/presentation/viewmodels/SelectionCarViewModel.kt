package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Car
import kotlinx.coroutines.launch
import javax.inject.Inject

class SelectionCarViewModel @Inject constructor(
) : ViewModel() {

    private var allCars: List<Car> = emptyList()

    private val _carList = MutableLiveData<List<Car>>()
    val carList: LiveData<List<Car>> get() = _carList

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _errorState = MutableLiveData<String?>()
    val errorState: LiveData<String?> get() = _errorState

    fun loadData(
        loadingState: Boolean = true,
        getCars: suspend () -> List<Car>
    ) {
        _loadingState.value = loadingState
        _errorState.value = null

        viewModelScope.launch {
            try {
                val car = getCars()
                _carList.value = car
            } catch (e: Exception) {
                _errorState.value = "Не удалось загрузить список заявок: ${e.localizedMessage}"
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun filterCars(pattern: String) {
        if (pattern.isBlank()) {
            _carList.value = allCars
            return
        }
        _carList.value = allCars.filter { car ->
            val searchPattern = pattern.lowercase().trim()
            car.brand.lowercase().contains(searchPattern) ||
                    car.licensePlate.lowercase().contains(searchPattern)
        }
    }

    fun clearFilter() {
        _carList.value = allCars
    }
}