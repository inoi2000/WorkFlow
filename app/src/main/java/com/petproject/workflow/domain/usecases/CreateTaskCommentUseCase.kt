package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.repositories.TaskRepository
import javax.inject.Inject

class CreateTaskCommentUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(comment: Comment) = repository.createTaskComment(comment)
}