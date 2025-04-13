package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.Task
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class AssignTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task) = repository.assignTask(task)
}