package com.petproject.workflow.domain.entities

import java.io.File

data class Comment(
    val id: String = "",
    val text: String,
    val files: List<File>? = null
)
