package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
class Instruction(
    val id: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val data: String,
    val status: DocumentStatus,
    val isConfirmed: Boolean,
    val instructorId: String,
    val employeeId: String,
    val confirmedAt: LocalDateTime? = null,
    val validUntil: LocalDate? = null
): Parcelable