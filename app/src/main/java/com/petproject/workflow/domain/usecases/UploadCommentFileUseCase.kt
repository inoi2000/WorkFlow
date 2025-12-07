package com.petproject.workflow.domain.usecases

import android.net.Uri
import com.petproject.workflow.domain.repositories.CommentRepository
import javax.inject.Inject

data class UploadCommentFileUseCase @Inject constructor(
    private val repository: CommentRepository
) {
    suspend operator fun invoke(uri: Uri) = repository.uploadFile(uri)
}