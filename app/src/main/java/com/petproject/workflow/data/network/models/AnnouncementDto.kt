package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.FileKey

data class AnnouncementDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("file_key")
    @Expose
    val fileKey: FileKey?,
)
