package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class AbsenceDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("start_date")
    @Expose
    val startDate: String,
    @SerializedName("end_date")
    @Expose
    val endDate: String,
    @SerializedName("place")
    @Expose
    val place: String?,
    @SerializedName("employee")
    @Expose
    val employee: EmployeeDto,
    @SerializedName("created_by")
    @Expose
    val createdBy: EmployeeDto,
    @SerializedName("policy")
    @Expose
    val policy: PolicyDto,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,

)