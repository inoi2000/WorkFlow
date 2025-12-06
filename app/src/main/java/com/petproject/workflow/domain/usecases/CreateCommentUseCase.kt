package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.repositories.CommentRepository
import javax.inject.Inject

class CreateCommentUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(comment: Comment) = repository.createComment(comment)
}