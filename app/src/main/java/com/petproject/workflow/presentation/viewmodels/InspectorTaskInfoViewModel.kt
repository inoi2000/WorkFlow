package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.ApproveTaskUseCase
import com.petproject.workflow.domain.usecases.CancelTaskUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import com.petproject.workflow.domain.usecases.RejectTaskUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
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

    val commentsCount: LiveData<String> get() = inspectorTask.map { it.commentsCount.toString() }

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        if (throwable is IOException) {
            _errorMessage.value = throwable.message
        }
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _inspectorTask.value = getTaskByIdUseCase(taskId)
        }
    }

    fun approvalTask() {
        viewModelScope.launch(exceptionHandler) {
            _inspectorTask.value = approveTaskUseCase(taskId)
            _successMessage.value = "Задача успешно одобрена"
        }
    }

    fun rejectTask() {
        viewModelScope.launch(exceptionHandler) {
            _inspectorTask.value = rejectTaskUseCase(taskId)
            _successMessage.value = "Задача отправлена на доработку"
        }
    }

    fun cancelTask() {
        viewModelScope.launch(exceptionHandler) {
            _inspectorTask.value = cancelTaskUseCase(taskId)
            _successMessage.value = "Задача отменена"
        }
    }
}