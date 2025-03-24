package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.GetExecutingTaskUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExecutorTaskInfoViewModel @Inject constructor(
    private val taskId: String,
    private val getExecutingTaskUseCase: GetExecutingTaskUseCase
): ViewModel() {

    private val _executingTask = MutableLiveData<Task>()
    val executingTask: LiveData<Task> get() = _executingTask

    val commentsCount: String get() = executingTask.value?.commentsCount.toString()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _executingTask.value = getExecutingTaskUseCase(taskId)
        }
    }
}