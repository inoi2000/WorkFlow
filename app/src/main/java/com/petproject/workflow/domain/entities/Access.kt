package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalDateTime

@Parcelize
data class Access(
    val id: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val type: AccessDurationType,
    val status: DocumentStatus,
    val issuerId: String,
    val holderId: String,
    val data: Data,
    val validUntil: LocalDate? = null,
): Parcelable