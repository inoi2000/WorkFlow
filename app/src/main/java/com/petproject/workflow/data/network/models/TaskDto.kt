package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.Comment
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
    @SerializedName("priority")
    @Expose
    val priority: String,
    @SerializedName("destination")
    @Expose
    val destination: String?,
    @SerializedName("creation")
    @Expose
    val creation: String?,
    @SerializedName("deadline")
    @Expose
    val deadline: String?,
    @SerializedName("executor")
    @Expose
    val executorId: String?,
    @SerializedName("inspector")
    @Expose
    val inspectorId: String?,
    @SerializedName("shouldBeInspected")
    @Expose
    val shouldBeInspected: Boolean,
    @SerializedName("comments")
    @Expose
    val comments: List<Comment>,
)