package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DateTimeUpdateDto(
    @SerializedName("object_id")
    @Expose
    val objectId: String,
    @SerializedName("updated_time")
    @Expose
    val updatedTime: String
)
