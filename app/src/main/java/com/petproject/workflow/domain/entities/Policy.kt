package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Policy(
    val id: String,
    val type: AbsenceType,
    val maxDurationDays: Int,
    val requiresApproval: Boolean,
    val canApprovePositionIds: List<String>
): Parcelable
