package com.petproject.workflow.domain.repositories

import android.net.Uri
import com.petproject.workflow.domain.entities.Comment
import com.petproject.workflow.domain.entities.FileKey

interface CommentRepository {
    suspend fun getTaskComments(taskId: String): List<Comment>

    suspend fun createComment(comment: Comment): Boolean

    suspend fun uploadFile(uri: Uri): FileKey

    suspend fun deleteFile(fileKeyId: String): Boolean
}