package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class Vacation(
    val id: String = "",
    val start: LocalDate,
    val end: LocalDate,
    val isApproval: Boolean = true
)
