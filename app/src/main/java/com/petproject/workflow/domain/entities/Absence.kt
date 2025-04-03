package com.petproject.workflow.domain.entities

import java.time.LocalDate

data class Absence(
    val id: String = "",
    val type: AbsenceType,
    val status: AbsenceStatus,
    val start: LocalDate,
    val end: LocalDate,
    val place: String? = null,
    val isApproval: Boolean = true
)
