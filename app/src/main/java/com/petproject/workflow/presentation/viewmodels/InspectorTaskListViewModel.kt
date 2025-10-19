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
import com.petproject.workflow.domain.usecases.GetAllCurrentInspectorTasksUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class InspectorTaskListViewModel @Inject constructor(
    private val getAllCurrentInspectorTasksUseCase: GetAllCurrentInspectorTasksUseCase
) : ViewModel() {

    sealed class TaskListUiState {
        object Loading : TaskListUiState()
        data class Success(val tasks: List<Task>) : TaskListUiState()
        data class Error(val message: String) : TaskListUiState()
    }

    private val _uiState = MutableLiveData<TaskListUiState>(TaskListUiState.Loading)
    val uiState: MutableLiveData<TaskListUiState> get() = _uiState

    private val _tasksList = MutableLiveData<List<Task>>()
    private val _filteredTaskList = MutableLiveData<List<Task>>()

    val filteredTaskList: LiveData<List<Task>> get() = _filteredTaskList

    val onApprovalTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count { it.status == TaskStatus.ON_APPROVAL }
    }

    val allTasksCount: LiveData<Int> = _tasksList.map { taskList ->
        taskList.count()
    }

    // Текущие активные фильтры
    private var currentSearchQuery: String = ""
    private var currentActiveFilter: ActiveFilter? = null

    private var allTasks: List<Task> = emptyList()

    sealed class ActiveFilter {
        data class StatusFilter(val status: TaskStatus) : ActiveFilter()
        data class PriorityFilter(val priority: TaskPriority) : ActiveFilter()
        object AllTasksFilter : ActiveFilter()
    }

    init {
        loadData()
    }

    fun loadData() {
        _uiState.value = TaskListUiState.Loading
        viewModelScope.launch {
            try {
                val tasks = getAllCurrentInspectorTasksUseCase()
                allTasks = tasks
                _tasksList.value = tasks
                applyFilters()
                _uiState.value = TaskListUiState.Success(tasks)
            } catch (e: Exception) {
                _uiState.value = TaskListUiState.Error(
                    "Не удалось загрузить список назначений: ${e.localizedMessage}"
                )
                _tasksList.value = emptyList()
                _filteredTaskList.value = emptyList()
            }
        }
    }

    fun retry() {
        loadData()
    }

    fun clearAllFilters() {
        currentSearchQuery = ""
        currentActiveFilter = null
        applyFilters()
    }

    fun filteredTaskListByDefault() {
        currentActiveFilter = ActiveFilter.AllTasksFilter
        applyFilters()
    }

    fun filteredTaskListByStatus(status: TaskStatus) {
        // Устанавливаем только один активный фильтр - статус
        currentActiveFilter = ActiveFilter.StatusFilter(status)
        applyFilters()
    }

    fun filteredTaskListByPriority(priority: TaskPriority) {
        // Устанавливаем только один активный фильтр - приоритет
        currentActiveFilter = ActiveFilter.PriorityFilter(priority)
        applyFilters()
    }

    fun searchTasks(query: String) {
        currentSearchQuery = query
        applyFilters()
    }

    private fun applyFilters() {
        var filtered = allTasks.asSequence()

        // Apply search filter (работает вместе с другими фильтрами)
        if (currentSearchQuery.isNotBlank()) {
            filtered = filtered.filter { task ->
                task.description.contains(currentSearchQuery, true) ||
                        task.executor?.name?.contains(currentSearchQuery, true) == true ||
                        task.inspector?.name?.contains(currentSearchQuery, true) == true
            }
        }

        // Apply active filter (только один активный фильтр за раз)
        currentActiveFilter?.let { filter ->
            when (filter) {
                is ActiveFilter.StatusFilter -> {
                    filtered = filtered.filter { it.status == filter.status }
                }
                is ActiveFilter.PriorityFilter -> {
                    filtered = filtered.filter { it.priority == filter.priority }
                }
                ActiveFilter.AllTasksFilter -> {
                    // No additional filtering for all tasks
                }
            }
        }

        val result = filtered.toList()
        _filteredTaskList.value = result

        // Update UI state based on filtered results
        if (result.isEmpty() && (currentSearchQuery.isNotBlank() || currentActiveFilter != null)) {
            _uiState.value = TaskListUiState.Success(emptyList())
        } else if (result.isNotEmpty()) {
            _uiState.value = TaskListUiState.Success(result)
        }
    }

    // Методы для определения текущего активного фильтра (для выделения карточек во Fragment)
    fun getActiveStatusFilter(): TaskStatus? {
        return when (currentActiveFilter) {
            is ActiveFilter.StatusFilter -> (currentActiveFilter as ActiveFilter.StatusFilter).status
            else -> null
        }
    }

    fun getActivePriorityFilter(): TaskPriority? {
        return when (currentActiveFilter) {
            is ActiveFilter.PriorityFilter -> (currentActiveFilter as ActiveFilter.PriorityFilter).priority
            else -> null
        }
    }

    fun isAllTasksFilterActive(): Boolean {
        return currentActiveFilter is ActiveFilter.AllTasksFilter
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