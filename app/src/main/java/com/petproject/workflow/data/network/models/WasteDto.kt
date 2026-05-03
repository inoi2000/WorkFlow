package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class WasteDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("is_recycle")
    @Expose
    val isRecycle: Boolean,
    @SerializedName("allowed_storages")
    @Expose
    val allowedStorages: List<StorageDto>?
)