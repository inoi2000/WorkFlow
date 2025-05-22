package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate

@Parcelize
data class Absence(
    val id: String = "",
    val type: AbsenceType,
    val status: AbsenceStatus,
    val start: LocalDate,
    val end: LocalDate,
    val place: String? = null,
    val isApproval: Boolean = true
) : Parcelable
