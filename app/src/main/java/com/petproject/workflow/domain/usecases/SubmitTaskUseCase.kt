package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

data class SubmitTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: String) = repository.submitTask(taskId)
}
