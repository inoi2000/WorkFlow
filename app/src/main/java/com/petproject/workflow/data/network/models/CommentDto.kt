package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.FileKey

data class CommentDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("text")
    @Expose
    val text: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("commentStatus")
    @Expose
    val commentStatus: String,
    @SerializedName("taskId")
    @Expose
    val taskId: String,
    @SerializedName("file_keys")
    @Expose
    val fileKeys: List<FileKey>?
)