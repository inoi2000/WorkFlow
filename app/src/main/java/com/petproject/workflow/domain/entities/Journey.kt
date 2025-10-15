package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Journey(
    val id: String = "",
    val car: Car,
    val driver: Employee,
    val status: JourneyStatus,
    val startOdometer: Double,
    val endOdometer: Double,
    val createdAt: LocalDateTime,
    val confirmedAt: LocalDateTime?,
    val startedAt: LocalDateTime?,
    val finishedAt: LocalDateTime?,
    val canceledAt: LocalDateTime?,
    val trailer: Trailer?,
    val statement: Statement?
): Parcelable
