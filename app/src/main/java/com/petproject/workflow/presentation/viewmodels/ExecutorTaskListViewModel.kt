package com.petproject.workflow.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.R
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.usecases.GetAllCurrentExecutorTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class ExecutorTaskListViewModel @Inject constructor(
    private val getAllCurrentExecutorTasksUseCase: GetAllCurrentExecutorTasksUseCase
) : ViewModel() {

    private val _tasksList = MutableLiveData<List<Task>>()
    private val _filteredTaskList = MutableLiveData<List<Task>>()
    private val _isLoading = MutableLiveData<Boolean>()

    val filteredTaskList: LiveData<List<Task>> get() = _filteredTaskList
    val isLoading: LiveData<Boolean> get() = _isLoading

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

    private var currentSearchQuery: String = ""
    private var currentStatusFilter: TaskStatus? = null
    private var currentPriorityFilter: TaskPriority? = null

    init {
        loadData()
    }

    fun loadData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _tasksList.value = getAllCurrentExecutorTasksUseCase()
                applyFilters()
            } catch (e: Exception) {
                _tasksList.value = emptyList()
                _filteredTaskList.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun filteredTaskListByDefault() {
        currentStatusFilter = null
        currentPriorityFilter = null
        applyFilters()
    }

    fun filteredTaskListByStatus(status: TaskStatus) {
        currentStatusFilter = status
        currentPriorityFilter = null
        applyFilters()
    }

    fun filteredTaskListByPriority(priority: TaskPriority) {
        currentPriorityFilter = priority
        currentStatusFilter = null
        applyFilters()
    }

    fun searchTasks(query: String) {
        currentSearchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        val tasks = _tasksList.value ?: emptyList()

        var filtered = tasks.asSequence()

        // Apply search filter
        if (currentSearchQuery.isNotBlank()) {
            filtered = filtered.filter { task ->
                task.description.contains(currentSearchQuery, true) ||
                        task.destination?.contains(currentSearchQuery, true) == true ||
                        task.executor?.name?.contains(currentSearchQuery, true) == true ||
                        task.inspector?.name?.contains(currentSearchQuery, true) == true
            }
        }

        // Apply status filter
        currentStatusFilter?.let { status ->
            filtered = filtered.filter { it.status == status }
        }

        // Apply priority filter
        currentPriorityFilter?.let { priority ->
            filtered = filtered.filter { it.priority == priority }
        }

        _filteredTaskList.value = filtered.toList()
    }

    fun getStatusDisplayName(status: TaskStatus, context: Context): String {
        return when (status) {
            TaskStatus.NEW -> context.getString(R.string.status_new)
            TaskStatus.IN_PROGRESS -> context.getString(R.string.status_in_progress)
            TaskStatus.COMPLETED -> context.getString(R.string.status_completed)
            TaskStatus.FAILED -> context.getString(R.string.status_failed)
            TaskStatus.ON_APPROVAL -> context.getString(R.string.status_on_approval)
            TaskStatus.NOT_APPROVAL -> context.getString(R.string.status_not_approval)
            TaskStatus.CANCELED -> context.getString(R.string.status_canceled)
        }
    }

    fun getPriorityDisplayName(priority: TaskPriority, context: Context): String {
        return when (priority) {
            TaskPriority.COMMON -> context.getString(R.string.priority_common)
            TaskPriority.URGENT -> context.getString(R.string.priority_urgent)
        }
    }
}