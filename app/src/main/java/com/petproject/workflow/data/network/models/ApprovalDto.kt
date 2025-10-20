package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class ApprovalDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("absence")
    @Expose
    val absence: AbsenceDto,
    @SerializedName("approver")
    @Expose
    val approver: EmployeeDto,
    @SerializedName("description")
    @Expose
    val description: String,
)