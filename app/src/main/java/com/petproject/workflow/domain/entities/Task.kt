package com.petproject.workflow.domain.entities

import java.time.LocalDate
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Task(
    val id: String = "",
    val description: String,
    val status: TaskStatus,
    val priority: TaskPriority,
    val createdAt: LocalDateTime? = null,
    val deadline: LocalDate? = null,
    val executor: Employee? = null,
    val inspector: Employee? = null,
    val comments: List<Comment>? = null,

    val shouldBeInspected: Boolean
) : Parcelable {
    val commentsCount: Int get() = comments?.count() ?: 0
}
