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
    val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase
): ViewModel() {

    val dateFormatPattern = "dd-MM-yyyy"

    private val _errorInputDescription = MutableLiveData(false)
    val errorInputDescription: LiveData<Boolean> get() = _errorInputDescription

    private val _errorInputDeadline = MutableLiveData(false)
    val errorInputDeadline: LiveData<Boolean> get() = _errorInputDeadline

    private val _navigateToDoneCreateTaskScreen = MutableLiveData<Task?>()
    val navigateToDoneCreateTaskScreen: LiveData<Task?> get() = _navigateToDoneCreateTaskScreen

    var descriptionField: ObservableField<String> = ObservableField()
    var deadlineField: ObservableField<String> = ObservableField()
    var destinationField: ObservableField<String> = ObservableField()

    var priorityField: Boolean = false
    var shouldBeInspectedField: Boolean = false

    fun createTask(executor: Employee) {
        val description = descriptionField.get() ?: ""

        val deadlineString = deadlineField.get() ?: ""
        val deadline: LocalDate = if (deadlineString.isNotBlank()) {
            LocalDate.parse(deadlineString, DateTimeFormatter.ofPattern(dateFormatPattern))
        } else {
            _errorInputDeadline.value = true
            return
        }

        viewModelScope.launch {
            if (validateInput(description, deadline)) {
                val priority = if (priorityField) {
                    TaskPriority.URGENT
                } else {
                    TaskPriority.COMMON
                }

                val tempTask = Task(
                    description = description,
                    status = TaskStatus.NEW,
                    priority = priority,
                    creation = LocalDate.now(),
                    deadline = deadline,
                    destination = destinationField.get(),
                    executor = executor,
                    inspector = getCurrentEmployeeUseCase(),
                    comments = emptyList(),
                    shouldBeInspected = shouldBeInspectedField
                )
                _navigateToDoneCreateTaskScreen.value = tempTask
            }
        }
    }

    private fun validateInput(description: String, deadline: LocalDate): Boolean {
        var result = true
        if (description.isBlank()) {
            _errorInputDescription.value = true
            result = false
        }
        if (deadline < LocalDate.now()) {
            _errorInputDeadline.value = true
            result = false
        }
        return result
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