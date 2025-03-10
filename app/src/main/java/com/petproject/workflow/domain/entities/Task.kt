package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class Task(
    val id: String = "",
    val description: String,
    val status: TaskStatus,
    val creation: LocalDate? = null,
    val deadline: LocalDate? = null,
    val executor: Employee? = null,
    val inspector: Employee? = null
)
