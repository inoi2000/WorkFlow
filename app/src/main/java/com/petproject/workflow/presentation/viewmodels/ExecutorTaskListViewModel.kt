package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.usecases.GetAllExecutingTasksUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class ExecutorTaskListViewModel @Inject constructor(
    private val getAllExecutingTasksUseCase: GetAllExecutingTasksUseCase
) : ViewModel() {

    private val _tasksList = MutableLiveData<List<Task>>()

    private val _filteredTaskList = MutableLiveData<List<Task>>()
    val filteredTaskList: LiveData<List<Task>> get() = _filteredTaskList

    val overdueTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.status == TaskStatus.FAILED }
    }

    val urgentTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.priority == TaskPriority.URGENT }
    }

    val onApprovalTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.status == TaskStatus.ON_APPROVAL }
    }

    val finishedTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.status == TaskStatus.COMPLETED }
    }

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _tasksList.value = getAllExecutingTasksUseCase()
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