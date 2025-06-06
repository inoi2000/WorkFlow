package com.petproject.workflow.domain.entities

import java.time.LocalDate
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val id: String = "",
    val description: String,
    val status: TaskStatus,
    val priority: TaskPriority,
    val creation: LocalDate? = null,
    val deadline: LocalDate? = null,
    val destination: String? = null,
    val executor: Employee? = null,
    val inspector: Employee? = null,
    val comments: List<Comment>? = null,

    val shouldBeInspected: Boolean
) : Parcelable {
    val commentsCount: Int get() = comments?.count() ?: 0
}
