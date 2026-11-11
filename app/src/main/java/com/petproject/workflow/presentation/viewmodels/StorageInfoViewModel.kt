package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.entities.StoragePlacement
import com.petproject.workflow.domain.usecases.GetStorageByIdUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class StorageInfoViewModel @Inject constructor(
    private val storageId: String,
    private val getStorageByIdUseCase: GetStorageByIdUseCase,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _storage = MutableLiveData<Storage>()
    val storage: LiveData<Storage> get() = _storage

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    // Форматированные данные для отображения
    val formattedFillPercent: LiveData<String> = storage.map { storage ->
        val percent = if (storage.totalVolume > 0) {
            (storage.currentVolume / storage.totalVolume * 100).toInt()
        } else 0
        "$percent %"
    }

    val fillProgress: LiveData<Int> = storage.map { storage ->
        if (storage.totalVolume > 0) {
            ((storage.currentVolume / storage.totalVolume) * 100).toInt()
        } else 0
    }

    val formattedCurrentProduct: LiveData<String> = storage.map { storage ->
        storage.currentProduct?.name ?: "Нет продукта"
    }

    val formattedCurrentVolume: LiveData<String> = storage.map { storage ->
        String.format("%.1f м³", storage.currentVolume)
    }

    val formattedTotalVolume: LiveData<String> = storage.map { storage ->
        String.format("%.1f м³", storage.totalVolume)
    }

    val formattedCurrentWeight: LiveData<String> = storage.map { storage ->
        val weight = storage.currentVolume * (storage.currentProduct?.density ?: 0.0)
        String.format("%.1f т", weight)
    }

    val formattedMaxWeight: LiveData<String> = storage.map { storage ->
        val maxWeight = storage.totalVolume * (storage.currentProduct?.density ?: 0.0)
        String.format("%.1f т", maxWeight)
    }

    val formattedPlacement: LiveData<String> = storage.map { storage ->
        when (storage.placement) {
            StoragePlacement.ABOVE_GROUND -> "Наземный"
            StoragePlacement.UNDERGROUND -> "Подземный"
        }
    }

    val formattedAllowedMixing: LiveData<String> = storage.map { storage ->
        if (storage.allowedMixing) "Разрешено" else "Запрещено"
    }

    val formattedOutputProduct: LiveData<String> = storage.map { storage ->
        storage.outputProduct?.name ?: "Не указан"
    }

    val formattedUpdatedAt: LiveData<String> = storage.map { storage ->
        storage.updatedAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    // Управление видимостью кнопок действий
    val showPourButton: LiveData<Boolean> = storage.map { storage ->
        // Показываем кнопку заливки, если есть место и продукт для заливки
        storage.currentVolume < storage.totalVolume && storage.allowedProducts?.isNotEmpty() == true
    }

    val showDrainButton: LiveData<Boolean> = storage.map { storage ->
        // Показываем кнопку слива, если есть продукт
        storage.currentProduct != null && storage.currentVolume > 0
    }

    val showCheckButton: LiveData<Boolean> = storage.map { storage ->
        // Кнопка проверки доступна всегда
        true
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
                val storage = getStorageByIdUseCase(storageId)
                _storage.value = storage
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun pourStorage(amount: Double, productId: String) {
//        _isLoading.value = true
//        viewModelScope.launch(exceptionHandler) {
//            try {
//                val updatedStorage = pourStorageUseCase(storageId, amount, productId)
//                _storage.value = updatedStorage
//                _successMessage.value = "Заливка выполнена успешно"
//                _isError.value = false
//            } catch (e: Exception) {
//                _errorMessage.value = "Ошибка при заливке: ${e.message}"
//                throw e
//            } finally {
//                _isLoading.value = false
//            }
//        }
    }

    fun drainStorage(amount: Double) {
//        _isLoading.value = true
//        viewModelScope.launch(exceptionHandler) {
//            try {
//                val updatedStorage = drainStorageUseCase(storageId, amount)
//                _storage.value = updatedStorage
//                _successMessage.value = "Слив выполнен успешно"
//                _isError.value = false
//            } catch (e: Exception) {
//                _errorMessage.value = "Ошибка при сливе: ${e.message}"
//                throw e
//            } finally {
//                _isLoading.value = false
//            }
//        }
    }

    fun checkStorage() {
//        _isLoading.value = true
//        viewModelScope.launch(exceptionHandler) {
//            try {
//                val updatedStorage = checkStorageUseCase(storageId)
//                _storage.value = updatedStorage
//                _successMessage.value = "Проверка склада выполнена"
//                _isError.value = false
//            } catch (e: Exception) {
//                _errorMessage.value = "Ошибка при проверке: ${e.message}"
//                throw e
//            } finally {
//                _isLoading.value = false
//            }
//        }
    }

    fun errorMessageShown() {
        _errorMessage.value = null
    }

    fun successMessageShown() {
        _successMessage.value = null
    }
}