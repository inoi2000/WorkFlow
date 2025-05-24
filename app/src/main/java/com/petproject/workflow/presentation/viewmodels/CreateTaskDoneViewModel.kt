package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.usecases.CreateTaskUseCase
import javax.inject.Inject

class CreateTaskDoneViewModel @Inject constructor(
    private val createTaskUseCase: CreateTaskUseCase
): ViewModel() {

    suspend fun createTask(task: Task): Boolean {
        return createTaskUseCase(task)
    }
}