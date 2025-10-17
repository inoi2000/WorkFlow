package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class StatementDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("logist")
    @Expose
    val logist: EmployeeDto,
    @SerializedName("data")
    @Expose
    val data: String,
    @SerializedName("contact_phone")
    @Expose
    val contactPhone: String,
    @SerializedName("destination_time")
    @Expose
    val destinationTime: String,
    @SerializedName("destination_address")
    @Expose
    val destinationAddress: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("journey")
    @Expose
    val journey: JourneyDto?
)
