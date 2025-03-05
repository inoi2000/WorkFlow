package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class BusinessTrip(
    val id: String = "",
    val start: LocalDate,
    val end: LocalDate,
    val place: String,
    val isApproval: Boolean = true
)
