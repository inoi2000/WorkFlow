package com.petproject.workflow.data.network.models

import java.time.LocalDate
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BusinessTripDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("start")
    @Expose
    val start: LocalDate,
    @SerializedName("end")
    @Expose
    val end: LocalDate,
    @SerializedName("place")
    @Expose
    val place: String
)