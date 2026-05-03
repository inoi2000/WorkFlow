package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class StorageDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("total_volume")
    @Expose
    val totalVolume: Double,
    @SerializedName("current_volume")
    @Expose
    val currentVolume: Double,
    @SerializedName("current_waste")
    @Expose
    val currentWaste: WasteDto?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("allowed_wastes")
    @Expose
    val allowedWastes: List<WasteDto>?
)