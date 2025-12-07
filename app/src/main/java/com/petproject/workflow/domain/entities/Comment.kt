package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Comment(
    val id: String,
    val text: String,
    val createdAt: LocalDateTime,
    val commentStatus: CommentStatus,
    val taskId: String,
    val fileKeys: List<FileKey>? = null
) : Parcelable