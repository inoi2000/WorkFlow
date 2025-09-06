package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AnnouncementDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("postData")
    @Expose
    val postData: String,
    @SerializedName("content")
    @Expose
    val content: String,
    @SerializedName("imgUrl")
    @Expose
    val imgUrl: String?
)
