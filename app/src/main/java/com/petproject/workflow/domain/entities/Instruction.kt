package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class Instruction(
    val id: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val status: DocumentStatus,
    val instructorId: String,
    val data: Data,
    val validUntil: LocalDate? = null,
    val instructionConfirmation: InstructionConfirmation?,
    val instructionConfirmations: List<InstructionConfirmation>?
): Parcelable