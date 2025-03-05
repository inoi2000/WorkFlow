package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class Task(
    val id: String = "",
    val description: String,
    val status: TaskStatus,
    val creation: LocalDate?,
    val deadline: LocalDate?,
    val executor: Employee?,
    val inspector: Employee?
)
