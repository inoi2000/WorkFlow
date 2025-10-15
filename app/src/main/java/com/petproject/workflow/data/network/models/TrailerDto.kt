package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TrailerDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("brand")
    @Expose
    val brand: String,
    @SerializedName("licensePlate")
    @Expose
    val licensePlate: String,
    @SerializedName("volumeLiter")
    @Expose
    val volumeLiter: Double,
    @SerializedName("material")
    @Expose
    val material: String
)