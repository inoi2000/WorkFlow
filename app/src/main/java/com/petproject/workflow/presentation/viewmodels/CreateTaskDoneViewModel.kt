package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.entities.TaskPriority
import java.time.LocalDate
import javax.inject.Inject

class CreateTaskDoneViewModel @Inject constructor(

): ViewModel() {

    private val _task = MutableLiveData<Task>()
    val task: LiveData<Task> get() = _task

    private val _errorInputDescription = MutableLiveData(false)
    val errorInputDescription: LiveData<Boolean> get() = _errorInputDescription

    private val _errorInputDeadline = MutableLiveData(false)
    val errorInputDeadline: LiveData<Boolean> get() = _errorInputDeadline

    private val _navigateToDoneCreateTaskScreen = MutableLiveData<Task?>()
    val navigateToDoneCreateTaskScreen: LiveData<Task?> get() = _navigateToDoneCreateTaskScreen

    var descriptionField: ObservableField<String> = ObservableField()
    var deadlineField: ObservableField<LocalDate> = ObservableField()
    var priorityField: ObservableField<TaskPriority> = ObservableField()
    var shouldBeInspectedField: ObservableField<Boolean> = ObservableField()

    fun createTask(executorId: String) {
//        val task =
    }

    private fun validateInput(description: String, deadline: LocalDate): Boolean {
        return true
    }

}