package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Fuelling(
    val id: String = "",
    val driver: Employee,
    val operator: Employee,
    val car: Car,
    val volume: Double,
    val createdAt: LocalDateTime
): Parcelable
