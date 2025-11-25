package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("photo_key")
    @Expose
    val photoKey: String? = null,
    @SerializedName("phone")
    @Expose
    val phone: String? = null,
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("position")
    @Expose
    val position: PositionDto? = null,
    @SerializedName("department")
    @Expose
    val department: DepartmentDto? = null
)