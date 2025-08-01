package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EmployeeDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("position")
    @Expose
    val position: String?,
    @SerializedName("inspectionTasks")
    @Expose
    val inspectionTasks: List<TaskDto>?,
    @SerializedName("executionTasks")
    @Expose
    val executionTasks: List<TaskDto>?,
    @SerializedName("absence")
    @Expose
    val absence: List<AbsenceDto>?,
    @SerializedName("department")
    @Expose
    val department: DepartmentDto?,
    @SerializedName("canAssignTask")
    @Expose
    val canAssignTask: Boolean?
)