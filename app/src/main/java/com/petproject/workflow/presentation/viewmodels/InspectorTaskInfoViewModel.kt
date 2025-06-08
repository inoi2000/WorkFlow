package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.GetTaskByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectorTaskInfoViewModel @Inject constructor(
    private val taskId: String,
    private val getTaskByIdUseCase: GetTaskByIdUseCase
): ViewModel() {

    private val _inspectorTask = MutableLiveData<Task>()
    val inspectorTask: LiveData<Task> get() = _inspectorTask

    val commentsCount: String get() = inspectorTask.value?.commentsCount.toString()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _inspectorTask.value = getTaskByIdUseCase(taskId)
        }
    }
}