package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.CommentRepository
import javax.inject.Inject

data class DeleteCommentFileUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(fileKeyId: String) =
        repository.deleteFile(fileKeyId)
}