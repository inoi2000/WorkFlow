package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class JourneyDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("car")
    @Expose
    val car: CarDto,
    @SerializedName("driver")
    @Expose
    val driver: EmployeeDto,
    @SerializedName("status")
    @Expose
    val status: String,
    @SerializedName("start_odometer")
    @Expose
    val startOdometer: Double,
    @SerializedName("end_odometer")
    @Expose
    val endOdometer: Double,
    @SerializedName("created_at")
    @Expose
    val createdAt: String,
    @SerializedName("confirmed_at")
    @Expose
    val confirmedAt: String?,
    @SerializedName("started_at")
    @Expose
    val startedAt: String?,
    @SerializedName("finished_at")
    @Expose
    val finishedAt: String?,
    @SerializedName("canceled_at")
    @Expose
    val canceledAt: String?,
    @SerializedName("trailer")
    @Expose
    val trailer: TrailerDto?,
    @SerializedName("statement")
    @Expose
    val statement: StatementDto?
)
