package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.entities.JourneyStatus
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.usecases.GetAllCarsByStatusUseCase
import com.petproject.workflow.domain.usecases.GetCurrentEmployeeUseCase
import com.petproject.workflow.domain.usecases.GetDriverEmployeesUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import javax.inject.Inject

class CreateStatementViewModel @Inject constructor(
    val requestManager: RequestManager,
    val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase,
    val getAllCarsByStatusUseCase: GetAllCarsByStatusUseCase,
    val getAllDriverEmployeesUseCase: GetDriverEmployeesUseCase,
) : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _navigateToDone = MutableLiveData<Statement?>()
    val navigateToDone: LiveData<Statement?> get() = _navigateToDone

    private val _driver = MutableLiveData<Employee?>()
    val driver: LiveData<Employee?> get() = _driver

    private val _car = MutableLiveData<Car?>()
    val car: LiveData<Car?> get() = _car

    private val _trailer = MutableLiveData<Trailer?>()
    val trailer: LiveData<Trailer?> get() = _trailer

    val dateTimeFormatPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
    val dateFormatPattern = "dd.MM.yyyy"
    val timeFormatPattern = "HH:mm"

    private val _errorInputData = MutableLiveData<Boolean>()
    val errorInputData: LiveData<Boolean> get() = _errorInputData

    private val _errorInputContactPhone = MutableLiveData<Boolean>()
    val errorInputContactPhone: LiveData<Boolean> get() = _errorInputContactPhone

    private val _errorInputDestinationAddress = MutableLiveData<Boolean>()
    val errorInputDestinationAddress: LiveData<Boolean> get() = _errorInputDestinationAddress

    private val _errorInputDestinationDate = MutableLiveData<Boolean>()
    val errorInputDestinationDate: LiveData<Boolean> get() = _errorInputDestinationDate

    private val _errorInputDestinationTime = MutableLiveData<Boolean>()
    val errorInputDestinationTime: LiveData<Boolean> get() = _errorInputDestinationTime

    private val _errorFieldsDate = MutableLiveData<Boolean>()
    val errorFieldsDate: LiveData<Boolean> get() = _errorFieldsDate

    private val _errorDriver = MutableLiveData<Boolean>()
    val errorDriver: LiveData<Boolean> get() = _errorDriver

    private val _errorCar = MutableLiveData<Boolean>()
    val errorCar: LiveData<Boolean> get() = _errorCar

    // Поля формы
    val dataField = MutableLiveData<String>()
    val contactPhoneField = MutableLiveData<String>()
    val destinationAddressField = MutableLiveData<String>()
    val destinationDateField = MutableLiveData<String>()
    val destinationTimeField = MutableLiveData<String>()

    private val dateFormatter = DateTimeFormatter.ofPattern(dateFormatPattern)
    private val timeFormatter = DateTimeFormatter.ofPattern(timeFormatPattern)
    private val dateTimeFormatter = DateTimeFormatter.ofPattern(dateTimeFormatPattern)

    private fun validateInput(): Boolean {
        var isValid = true

        // Сброс ошибок
        _errorInputData.value = false
        _errorInputContactPhone.value = false
        _errorInputDestinationAddress.value = false
        _errorInputDestinationDate.value = false
        _errorInputDestinationTime.value = false
        _errorDriver.value = false
        _errorCar.value = false

        // Проверка описания
        if (dataField.value.isNullOrBlank()) {
            _errorInputData.value = true
            isValid = false
        }

        // Проверка телефона
        if (contactPhoneField.value.isNullOrBlank()) {
            _errorInputContactPhone.value = true
            isValid = false
        }

        // Проверка адреса
        if (destinationAddressField.value.isNullOrBlank()) {
            _errorInputDestinationAddress.value = true
            isValid = false
        }

        // Проверка даты
        val destinationDateString = destinationDateField.value
        if (destinationDateString.isNullOrBlank()) {
            _errorInputDestinationDate.value = true
            isValid = false
        } else {
            try {
                val date = LocalDate.parse(destinationDateString, dateFormatter)
                if (date.isBefore(LocalDate.now())) {
                    _errorInputDestinationDate.value = true
                    isValid = false
                }
            } catch (e: Exception) {
                _errorInputDestinationDate.value = true
                isValid = false
            }
        }

        // Проверка времени
        val destinationTimeString = destinationTimeField.value
        if (destinationTimeString.isNullOrBlank()) {
            _errorInputDestinationTime.value = true
            isValid = false
        } else {
            try {
                LocalTime.parse(destinationTimeString, timeFormatter)
            } catch (e: Exception) {
                _errorInputDestinationTime.value = true
                isValid = false
            }
        }

        // Проверка машины
        if (_car.value == null) {
            _errorCar.value = true
            isValid = false
        }

        // Проверка водителя
        if (_driver.value == null) {
            _errorDriver.value = true
            isValid = false
        }

        return isValid
    }

    fun setDriver(employee: Employee) {
        _driver.value = employee
        _errorDriver.value = false
    }

    fun removeDriver() {
        _driver.value = null
    }

    fun setCar(car: Car) {
        _car.value = car
        _errorCar.value = false
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

    fun createStatement() {
        viewModelScope.launch {
            _loadingState.value = true

            try {
                if (!validateInput()) {
                    throw IllegalArgumentException("Не все обязательные поля заполнены")
                }

                // Формируем дату и время доставки
                val date = LocalDate.parse(destinationDateField.value, dateFormatter)
                val time = destinationTimeField.value?.let {
                    if (it.contains(":")) it else "$it:00"
                } ?: "00:00"

//                val dateTimeFormatPattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS"
                val dateTimeString = "${date.format(DateTimeFormatter.ISO_LOCAL_DATE)}T$time:00.000000"
                val destinationDateTime = LocalDateTime.parse(dateTimeString, dateTimeFormatter)

                val logist = getCurrentEmployeeUseCase()
                val car =
                    _car.value ?: throw IllegalArgumentException("Не все обязательные поля заполнены")
                val trailer = _trailer.value
                val driver = _driver.value
                    ?: throw IllegalArgumentException("Не все обязательные поля заполнены")

                // Создаем Journey
                val journey = Journey(
                    id = UUID.randomUUID().toString(),
                    car = car,
                    driver = driver,
                    status = JourneyStatus.NEW,
                    startOdometer = 0.0,
                    endOdometer = 0.0,
                    createdAt = LocalDateTime.now(),
                    confirmedAt = null,
                    startedAt = null,
                    finishedAt = null,
                    canceledAt = null,
                    trailer = trailer,
                    statement = null
                )

                // Создаем Statement
                val statement = Statement(
                    id = UUID.randomUUID().toString(),
                    logist = logist,
                    data = dataField.value ?: "",
                    contactPhone = contactPhoneField.value ?: "",
                    destinationTime = destinationDateTime,
                    destinationAddress = destinationAddressField.value ?: "",
                    createdAt = LocalDateTime.now(),
                    updatedAt = LocalDateTime.now(),
                    journey = journey
                )

                // Отправляем результат
                _navigateToDone.value = statement

            } catch (e: Exception) {
                _errorFieldsDate.value = true
            } finally {
                _loadingState.value = false
            }
        }
    }

    fun onNavigationComplete() {
        _navigateToDone.value = null
    }
}