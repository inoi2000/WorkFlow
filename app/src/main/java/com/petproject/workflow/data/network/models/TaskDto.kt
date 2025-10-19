package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TaskDto(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("priority")
    @Expose
    val priority: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("deadline")
    @Expose
    val deadline: String,
    @SerializedName("executor")
    @Expose
    val executor: EmployeeDto? = null,
    @SerializedName("inspector")
    @Expose
    val inspector: EmployeeDto? = null,
    @SerializedName("shouldBeInspected")
    @Expose
    val shouldBeInspected: Boolean = false,
    @SerializedName("comments")
    @Expose
    val comments: List<CommentDto> = listOf(),
)