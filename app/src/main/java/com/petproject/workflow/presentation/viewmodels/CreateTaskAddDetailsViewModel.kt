package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import com.petproject.workflow.domain.entities.TaskStatus
import com.petproject.workflow.domain.usecases.GetCurrentEmployeeUseCase
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CreateTaskAddDetailsViewModel @Inject constructor(
    private val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase
) : ViewModel() {

    val dateFormatPattern = "dd.MM.yyyy"

    private val _errorInputDescription = MutableLiveData<Boolean>()
    val errorInputDescription: LiveData<Boolean> get() = _errorInputDescription

    private val _errorInputDeadline = MutableLiveData<Boolean>()
    val errorInputDeadline: LiveData<Boolean> get() = _errorInputDeadline

    private val _navigateToDoneCreateTaskScreen = MutableLiveData<Task?>()
    val navigateToDoneCreateTaskScreen: LiveData<Task?> get() = _navigateToDoneCreateTaskScreen

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    var descriptionField = ObservableField<String>()
    var deadlineField = ObservableField<String>()
    var destinationField = ObservableField<String>()

    var priorityField: Boolean = false
    var shouldBeInspectedField: Boolean = false

    fun createTask(executor: Employee) {
        _loadingState.value = true

        viewModelScope.launch {
            try {
                if (validateInput()) {
                    val task = buildTask(executor)
                    _navigateToDoneCreateTaskScreen.value = task
                }
            } catch (e: Exception) {
                // Обработка ошибок
            } finally {
                _loadingState.value = false
            }
        }
    }

    private suspend fun buildTask(executor: Employee): Task {
        val deadline = LocalDate.parse(
            deadlineField.get(),
            DateTimeFormatter.ofPattern(dateFormatPattern)
        )

        return Task(
            description = descriptionField.get().orEmpty(),
            status = TaskStatus.NEW,
            priority = if (priorityField) TaskPriority.URGENT else TaskPriority.COMMON,
            creation = LocalDate.now(),
            deadline = deadline,
            destination = destinationField.get(),
            executor = executor,
            inspector = getCurrentEmployeeUseCase(),
            comments = emptyList(),
            shouldBeInspected = shouldBeInspectedField
        )
    }

    private fun validateInput(): Boolean {
        var isValid = true

        if (descriptionField.get().isNullOrBlank()) {
            _errorInputDescription.value = true
            isValid = false
        }

        val deadlineString = deadlineField.get()
        if (deadlineString.isNullOrBlank()) {
            _errorInputDeadline.value = true
            isValid = false
        } else {
            try {
                val deadline = LocalDate.parse(deadlineString, DateTimeFormatter.ofPattern(dateFormatPattern))
                if (deadline < LocalDate.now()) {
                    _errorInputDeadline.value = true
                    isValid = false
                }
            } catch (e: Exception) {
                _errorInputDeadline.value = true
                isValid = false
            }
        }

        return isValid
    }

    fun resetErrorInputDescription() {
        _errorInputDescription.value = false
    }

    fun resetErrorInputDeadline() {
        _errorInputDeadline.value = false
    }

    fun onDoneCreateTaskScreenNavigated() {
        _navigateToDoneCreateTaskScreen.value = null
    }
}