package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.GetAllExecutingTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExecutingTaskListViewModel @Inject constructor(
    private val getAllExecutingTasksUseCase: GetAllExecutingTasksUseCase
): ViewModel() {

    private val _executingTasksList = MutableLiveData<List<Task>>()
    val executingTasksList: LiveData<List<Task>> get() = _executingTasksList

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _executingTasksList.value = getAllExecutingTasksUseCase()
        }
    }
}