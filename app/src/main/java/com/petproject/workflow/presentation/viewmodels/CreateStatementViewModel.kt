package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.usecases.CreateStatementUseCase
import com.petproject.workflow.domain.usecases.GetDriverEmployeesUseCase
import com.petproject.workflow.domain.usecases.GetStatementByIdUseCase
import javax.inject.Inject

class CreateStatementViewModel @Inject constructor(
    val requestManager: RequestManager,
    val getAllDriverEmployeesUseCase: GetDriverEmployeesUseCase,
    private val getEmployeeByIdUseCase: GetStatementByIdUseCase,
    private val getCarByIdUseCase: GetStatementByIdUseCase,
    private val getTrailerByIdUseCase: GetStatementByIdUseCase,
    private val createStatementUseCase: CreateStatementUseCase,
): ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _statementCreationResult = MutableLiveData<Boolean>()
    val statementCreationResult: LiveData<Boolean> get() = _statementCreationResult

    private val _driver = MutableLiveData<Employee?>()
    val driver: LiveData<Employee?> get() = _driver

    private val _car = MutableLiveData<Car?>()
    val car: LiveData<Car?> get() = _car

    private val _trailer = MutableLiveData<Trailer?>()
    val trailer: LiveData<Trailer?> get() = _trailer

    fun setDriver(employee: Employee) {
        _driver.value = employee
    }

    fun setCar(car: Car) {
        _car.value = car
    }

    fun setTrailer(trailer: Trailer) {
        _trailer.value = trailer
    }


}