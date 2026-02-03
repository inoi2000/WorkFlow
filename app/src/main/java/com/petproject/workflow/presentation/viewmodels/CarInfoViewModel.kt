package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.usecases.GetCarByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class CarInfoViewModel @Inject constructor(
    private val carId: String,
    private val getCarByIdUseCase: GetCarByIdUseCase
) : ViewModel() {

    private val _car = MutableLiveData<Car>()
    val car: LiveData<Car> get() = _car

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _car.value = getCarByIdUseCase(carId)
        }
    }
}