package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.usecases.GetAllInspectorTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectorTaskListViewModel @Inject constructor(
    private val getAllInspectorTasksUseCase: GetAllInspectorTasksUseCase
) : ViewModel() {

    private val _tasksList = MutableLiveData<List<Task>>()

    private val _filteredTaskList = MutableLiveData<List<Task>>()
    val filteredTaskList: LiveData<List<Task>> get() = _filteredTaskList

    val onApprovalTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.status == TaskStatus.ON_APPROVAL }
    }

    val allTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count()
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _tasksList.value = getAllInspectorTasksUseCase()
            filteredTaskListByDefault()
        }
    }

    fun filteredTaskListByDefault() {
        _filteredTaskList.value = _tasksList.value
    }

    fun filteredTaskListByStatus(status: TaskStatus) {
        _filteredTaskList.value = _tasksList.value?.filter { it.status == status }
    }

    fun filteredTaskListByPriority(priority: TaskPriority) {
        _filteredTaskList.value = _tasksList.value?.filter { it.priority == priority }
    }
}