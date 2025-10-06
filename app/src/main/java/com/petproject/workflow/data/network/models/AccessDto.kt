package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.AccessDurationType
import com.petproject.workflow.domain.entities.Data
import com.petproject.workflow.domain.entities.DocumentStatus
import java.time.LocalDate
import java.time.LocalDateTime

data class AccessDto(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("issuer_id")
    @Expose
    val issuerId: String,
    @SerializedName("holder_id")
    @Expose
    val holderId: String,
    @SerializedName("data")
    @Expose
    val data: Data,
    @SerializedName("valid_until")
    @Expose
    val validUntil: String? = null
)