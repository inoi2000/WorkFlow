package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.usecases.CreateTaskUseCase
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CreateTaskDoneViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
) : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _taskCreationResult = MutableLiveData<Boolean>()
    val taskCreationResult: LiveData<Boolean> get() = _taskCreationResult

    private val _navigateToTasks = MutableLiveData<Boolean>()
    val navigateToTasks: LiveData<Boolean> get() = _navigateToTasks

    // Данные для отображения в UI
    val executorName = ObservableField<String>()
    val deadline = ObservableField<String>()
    val priority = ObservableField<String>()
    val inspectionStatus = ObservableField<String>()

    private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.getDefault())

    fun setTaskData(task: Task) {
        executorName.set(task.executor?.name)
        deadline.set(task.deadline.format(dateFormatter))
        priority.set(
            when (task.priority) {
                TaskPriority.URGENT -> "Срочный"
                TaskPriority.COMMON -> "Обычный"
                else -> "Обычный"
            }
        )
        inspectionStatus.set(
            if (task.shouldBeInspected) "Требуется проверка" else "Без проверки"
        )
    }

    suspend fun createTask(task: Task): Boolean {
        _loadingState.value = true

        return try {
            val result = createTaskUseCase(task)
            _taskCreationResult.value = result

            if (result) {
                // Успешное создание задачи
                _loadingState.value = false
            } else {
                // Ошибка создания задачи
                _loadingState.value = false
            }
            result
        } catch (e: Exception) {
            _taskCreationResult.value = false
            _loadingState.value = false
            false
        }
    }

    fun onCreateAnotherTask() {
        // Навигация будет обработана во Fragment
    }

    fun onGoToTasks() {
        _navigateToTasks.value = true
    }

    fun onNavigationComplete() {
        _navigateToTasks.value = false
    }
}