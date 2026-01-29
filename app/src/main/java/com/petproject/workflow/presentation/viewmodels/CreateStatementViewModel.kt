package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.usecases.GetAllCarsByStatusUseCase
import com.petproject.workflow.domain.usecases.GetDriverEmployeesUseCase
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CreateStatementViewModel @Inject constructor(
    val requestManager: RequestManager,
    val getAllCarsByStatusUseCase: GetAllCarsByStatusUseCase,
    val getAllDriverEmployeesUseCase: GetDriverEmployeesUseCase,
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

    val dateTimeFormatPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"

    private val _errorInputData = MutableLiveData<Boolean>()
    val errorInputData: LiveData<Boolean> get() = _errorInputData

    private val _errorInputContactPhone = MutableLiveData<Boolean>()
    val errorInputContactPhone: LiveData<Boolean> get() = _errorInputContactPhone

    private val _errorInputDestinationAddress = MutableLiveData<Boolean>()
    val errorInputDestinationAddress: LiveData<Boolean> get() = _errorInputDestinationAddress

    private val _errorInputDestinationTime = MutableLiveData<Boolean>()
    val errorInputDestinationTime: LiveData<Boolean> get() = _errorInputDestinationTime

    var dataField = ObservableField<String>()
    var contactPhoneField = ObservableField<String>()
    var destinationAddressField = ObservableField<String>()
    var destinationTimeField = ObservableField<String>()

    private fun validateInput(): Boolean {
        var isValid = true

        if (dataField.get().isNullOrBlank()) {
            _errorInputData.value = true
            isValid = false
        }

        if (contactPhoneField.get().isNullOrBlank()) {
            _errorInputContactPhone.value = true
            isValid = false
        }

        if (destinationAddressField.get().isNullOrBlank()) {
            _errorInputDestinationAddress.value = true
            isValid = false
        }

        val destinationTimeString = destinationTimeField.get()
        if (destinationTimeString.isNullOrBlank()) {
            _errorInputDestinationTime.value = true
            isValid = false
        } else {
            try {
                val deadline = LocalDate.parse(destinationTimeString, DateTimeFormatter.ofPattern(dateTimeFormatPattern))
                if (deadline < LocalDate.now()) {
                    _errorInputDestinationTime.value = true
                    isValid = false
                }
            } catch (e: Exception) {
                _errorInputDestinationTime.value = false
                isValid = false
            }
        }

        return isValid
    }

    fun setDriver(employee: Employee) {
        _driver.value = employee
    }

    fun removeDriver() {
        _driver.value = null
    }

    fun setCar(car: Car) {
        _car.value = car
    }

    fun removeCar() {
        _car.value = null
    }

    fun setTrailer(trailer: Trailer) {
        _trailer.value = trailer
    }

    fun removeTrailer() {
        _trailer.value = null
    }


}