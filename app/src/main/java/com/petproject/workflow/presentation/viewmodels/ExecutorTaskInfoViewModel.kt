package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.AcceptTaskUseCase
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import com.petproject.workflow.domain.usecases.SubmitTaskUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class ExecutorTaskInfoViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskByIdUseCase: GetTaskByIdUseCase,
    private val acceptTaskUseCase: AcceptTaskUseCase,
    private val submitTaskUseCase: SubmitTaskUseCase
): ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    private val _successMessage = MutableLiveData<String?>()
    val successMessage: LiveData<String?> get() = _successMessage

    private val _executingTask = MutableLiveData<Task>()
    val executingTask: LiveData<Task> get() = _executingTask

    val commentsCount: LiveData<String> get() = executingTask.map { it.commentsCount.toString() }

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
            _executingTask.value = getTaskByIdUseCase(taskId)
        }
    }

    fun acceptTask() {
        viewModelScope.launch(exceptionHandler) {
            _executingTask.value = acceptTaskUseCase(taskId)
            _successMessage.value = "Задача принята в работу"
        }
    }

    fun submitTask() {
        viewModelScope.launch(exceptionHandler) {
            _executingTask.value = submitTaskUseCase(taskId)
            _successMessage.value = "Задача отправлена на утверждение"
        }
    }
}