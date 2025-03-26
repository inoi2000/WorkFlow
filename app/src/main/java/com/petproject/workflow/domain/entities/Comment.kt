package com.petproject.workflow.domain.entities

import java.io.File
import java.time.LocalDate

data class Comment(
    val id: String = "",
    val text: String,
    val creation: LocalDate,
    val task: Task? = null,
    val files: List<File>? = null
)
