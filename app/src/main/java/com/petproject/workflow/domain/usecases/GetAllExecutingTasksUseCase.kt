package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class GetAllExecutingTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke() = repository.getAllExecutingTasks()
}