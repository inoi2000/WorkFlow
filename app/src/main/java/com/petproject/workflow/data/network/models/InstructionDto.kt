package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.DocumentStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class InstructionDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: LocalDateTime,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: LocalDateTime,
    @SerializedName("data")
    @Expose
    val data: String,
    @SerializedName("status")
    @Expose
    val status: DocumentStatus,
    @SerializedName("is_confirmed")
    @Expose
    val isConfirmed: Boolean,
    @SerializedName("instructor_id")
    @Expose
    val instructorId: String,
    @SerializedName("employee_id")
    @Expose
    val employeeId: String,
    @SerializedName("confirmed_at")
    @Expose
    val confirmedAt: LocalDateTime? = null,
    @SerializedName("valid_until")
    @Expose
    val validUntil: LocalDate? = null
)