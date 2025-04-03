package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class AbsenceDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("status")
    @Expose
    val status: String,
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