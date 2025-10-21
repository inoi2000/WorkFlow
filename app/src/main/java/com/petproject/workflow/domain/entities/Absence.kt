package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class Absence(
    val id: String,
    val status: AbsenceStatus,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val place: String? = null,
    val employee: Employee,
    val createdBy: Employee,
    val policy: Policy,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
) : Parcelable
