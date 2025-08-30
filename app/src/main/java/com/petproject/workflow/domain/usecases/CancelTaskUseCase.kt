package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

data class CancelTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) = repository.cancelTask(taskId)
}
