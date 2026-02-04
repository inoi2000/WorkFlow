package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.usecases.ApproveTaskUseCase
import com.petproject.workflow.domain.usecases.CancelTaskUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import com.petproject.workflow.domain.usecases.RejectTaskUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class InspectorTaskInfoViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val rejectTaskUseCase: RejectTaskUseCase,
    private val approveTaskUseCase: ApproveTaskUseCase,
    private val cancelTaskUseCase: CancelTaskUseCase,
    val requestManager: RequestManager
): ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _inspectorTask = MutableLiveData<Task>()
    val inspectorTask: LiveData<Task> get() = _inspectorTask

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isError = MutableLiveData(false)
    val isError: LiveData<Boolean> get() = _isError

    val commentsCount: LiveData<String> get() = inspectorTask.map { it.commentsCount.toString() }

    // Форматированные данные для отображения
    val formattedStatus: LiveData<String> = inspectorTask.map { task ->
        when (task.status) {
            TaskStatus.NEW -> "Новая"
            TaskStatus.IN_PROGRESS -> "В работе"
            TaskStatus.COMPLETED -> "Выполнена"
            TaskStatus.FAILED -> "Не выполнена"
            TaskStatus.ON_APPROVAL -> "На проверке"
            TaskStatus.NOT_APPROVAL -> "Не одобрена"
            TaskStatus.CANCELED -> "Отменена"
        }
    }

    val formattedPriority: LiveData<String> = inspectorTask.map { task ->
        when (task.priority) {
            TaskPriority.COMMON -> "Обычный"
            TaskPriority.URGENT -> "Срочный"
        }
    }

    val formattedCreatedAt: LiveData<String> = inspectorTask.map { task ->
        task.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))
    }

    val formattedDeadline: LiveData<String> = inspectorTask.map { task ->
        task.deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
    }

    val isDeadlineSoon: LiveData<Boolean> = inspectorTask.map { task ->
        task.deadline.isBefore(LocalDate.now().plusDays(3))
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
                val task = getTaskByIdUseCase(taskId)
                _inspectorTask.value = task
                _isError.value = false
            } catch (e: Exception) {
                _isError.value = true
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun approvalTask() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val task = approveTaskUseCase(taskId)
                _inspectorTask.value = task
                _successMessage.value = "Задача успешно одобрена"
                _isError.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun rejectTask() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val task = rejectTaskUseCase(taskId)
                _inspectorTask.value = task
                _successMessage.value = "Задача отправлена на доработку"
                _isError.value = false
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cancelTask() {
        _isLoading.value = true
        viewModelScope.launch(exceptionHandler) {
            try {
                val task = cancelTaskUseCase(taskId)
                _inspectorTask.value = task
                _successMessage.value = "Задача отменена"
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