package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

data class VacationDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("start")
    @Expose
    val start: LocalDate,
    @SerializedName("end")
    @Expose
    val end: LocalDate
)