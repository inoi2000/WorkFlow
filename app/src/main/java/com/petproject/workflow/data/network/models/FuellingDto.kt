package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FuellingDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("driver")
    @Expose
    val driver: EmployeeDto,
    @SerializedName("operator")
    @Expose
    val operator: EmployeeDto,
    @SerializedName("car")
    @Expose
    val car: CarDto,
    @SerializedName("volume")
    @Expose
    val volume: Double,
    @SerializedName("createdAt")
    @Expose
    val createdAt: String
)