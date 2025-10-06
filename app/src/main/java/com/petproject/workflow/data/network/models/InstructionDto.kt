package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.Data

data class InstructionDto(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("instructor_id")
    @Expose
    val instructorId: String,
    @SerializedName("data")
    @Expose
    val data: Data,
    @SerializedName("valid_until")
    @Expose
    val validUntil: String? = null,
    @SerializedName("confirmation")
    @Expose
    val instructionConfirmation: InstructionConfirmationDto? = null,
    @SerializedName("confirmations")
    @Expose
    val instructionConfirmations: List<InstructionConfirmationDto>? = null
)