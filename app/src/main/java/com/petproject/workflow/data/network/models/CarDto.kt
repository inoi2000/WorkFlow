package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CarDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("brand")
    @Expose
    val brand: String,
    @SerializedName("model")
    @Expose
    val model: String,
    @SerializedName("license_plate")
    @Expose
    val licensePlate: String,
    @SerializedName("vin")
    @Expose
    val vin: String,
    @SerializedName("year")
    @Expose
    val year: Int,
    @SerializedName("color")
    @Expose
    val color: String,
    @SerializedName("odometer")
    @Expose
    val odometer: Double,
    @SerializedName("status")
    @Expose
    val status: String,
)