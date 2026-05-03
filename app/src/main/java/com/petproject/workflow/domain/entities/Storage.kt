package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Storage(
    val id: String,
    val name: String,
    val totalVolume: Double,
    val currentVolume: Double,
    val currentWaste: Waste?,
    val updatedAt: LocalDateTime,
    val allowedWastes: List<Waste>?
) : Parcelable