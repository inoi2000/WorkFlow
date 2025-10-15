package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Statement(
    val id: String = "",
    val logist: Employee,
    val data: String,
    val contactPhone: String,
    val address: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val journey: Journey?
): Parcelable
