package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class TaskDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("creation")
    @Expose
    val creation: LocalDate?,
    @SerializedName("deadline")
    @Expose
    val deadline: LocalDate?,
    @SerializedName("executor")
    @Expose
    val executor: EmployeeDto?,
    @SerializedName("inspector")
    @Expose
    val inspector: EmployeeDto?
)