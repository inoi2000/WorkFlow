package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PositionDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("level")
    @Expose
    val level: Int,
    @SerializedName("requires_special_documents")
    @Expose
    val requiresSpecialDocuments: Boolean,
)
