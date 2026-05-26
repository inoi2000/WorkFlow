package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class StorageEvent(
    val id: String,
    val actionType: StorageEventActionType,
    val volume: Double,
    val storage: Storage?,
    val product: Product?,
    val employee: Employee?,
    val createdAt: LocalDateTime
) : Parcelable
