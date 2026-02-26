package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.entities.JourneyStatus
import com.petproject.workflow.domain.usecases.ConfirmJourneyUseCase
import com.petproject.workflow.domain.usecases.FinishJourneyUseCase
import com.petproject.workflow.domain.usecases.GetJourneyByIdUseCase
import com.petproject.workflow.domain.usecases.StartJourneyUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class JourneyInfoViewModel @Inject constructor(
    private val journeyId: String,
    private val getJourneyByIdUseCase: GetJourneyByIdUseCase,
    private val confirmJourneyUseCase: ConfirmJourneyUseCase,
    private val startJourneyUseCase: StartJourneyUseCase,
    private val finishJourneyUseCase: FinishJourneyUseCase,
    val requestManager: RequestManager
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _journey = MutableLiveData<Journey>()
    val journey: LiveData<Journey> get() = _journey

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    // Форматированные данные для отображения
    val formattedStatus: LiveData<String> = journey.map { journey ->
        when (journey.status) {
            JourneyStatus.NEW -> "Новый"
            JourneyStatus.CONFIRMED -> "Согласован"
            JourneyStatus.STARTED -> "Начат"
            JourneyStatus.FINISHED -> "Закончен"
            JourneyStatus.CANCELED -> "Отменен"
        }
    }

    val formattedCreatedAt: LiveData<String> = journey.map { journey ->
        journey.statement?.createdAt?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            ?: ""
    }

    val formattedUpdatedAt: LiveData<String> = journey.map { journey ->
        journey.statement?.updatedAt?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            ?: ""
    }

    val formattedDestinationTime: LiveData<String> = journey.map { journey ->
        journey.statement?.destinationTime?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
            ?: ""
    }

    val formattedName: LiveData<String> = journey.map { journey ->
        val uuidString = journey.id
        try {
            val shortId = uuidString.take(8)
            "Выезд #$shortId"
        } catch (e: Exception) {
            "Выезд #${uuidString}"
        }
    }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _isLoading.value = false
        _isError.value = true
        if (throwable is IOException) {
            _errorMessage.value = "Ошибка сети. Проверьте подключение."
        } else {
            _errorMessage.value = "Произошла ошибка: ${throwable.message}"
        }
    }

    init {
        loadData()
    }

    fun loadData() {
        _isLoading.value = true
        _isError.value = false
        viewModelScope.launch(exceptionHandler) {
            try {
                val journey = getJourneyByIdUseCase(journeyId)
                _journey.value = journey
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun confirmJourney() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val dateTime = LocalDateTime.now()
                val journey = confirmJourneyUseCase(journeyId, dateTime)
                _journey.value = journey
                _successMessage.value = "Ознакомление с выездом прошло успешно"
                _isError.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun startJourney() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val dateTime = LocalDateTime.now()
                val journey = startJourneyUseCase(journeyId, dateTime)
                _journey.value = journey
                _successMessage.value = "Выезд успено начат"
                _isError.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun finishJourney() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val dateTime = LocalDateTime.now()
                val journey = finishJourneyUseCase(journeyId, dateTime)
                _journey.value = journey
                _successMessage.value = "Выезд успено завершен"
                _isError.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }


    fun errorMessageShown() {
        _errorMessage.value = null
    }

    fun successMessageShown() {
        _successMessage.value = null
    }
}