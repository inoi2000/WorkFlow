package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class PolicyDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("max_duration_days")
    @Expose
    val maxDurationDays: Int,
    @SerializedName("requires_approval")
    @Expose
    val requiresApproval: Boolean,
    @SerializedName("can_approve_position_ids")
    @Expose
    val canApprovePositionIds: List<String>,
)