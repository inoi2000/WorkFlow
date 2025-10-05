package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.Data
import com.petproject.workflow.domain.entities.DocumentStatus
import com.petproject.workflow.domain.entities.InstructionConfirmation
import java.time.LocalDate
import java.time.LocalDateTime

data class InstructionDto(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("created_at")
    @Expose
    val createdAt: LocalDateTime,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: LocalDateTime,
    @SerializedName("status")
    @Expose
    val status: DocumentStatus,
    @SerializedName("instructor_id")
    @Expose
    val instructorId: String,
    @SerializedName("data")
    @Expose
    val data: Data,
    @SerializedName("valid_until")
    @Expose
    val validUntil: LocalDate? = null,
    @SerializedName("confirmation")
    @Expose
    val instructionConfirmation: InstructionConfirmation? = null,
    @SerializedName("confirmations")
    @Expose
    val instructionConfirmations: List<InstructionConfirmation>? = null
)