package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.File
import java.time.LocalDate

@Parcelize
data class Comment(
    val id: String = "",
    val text: String,
    val creation: LocalDate,
    val commentStatus: CommentStatus,
    val taskId: String,
//    val files: List<File>? = null
) : Parcelable
