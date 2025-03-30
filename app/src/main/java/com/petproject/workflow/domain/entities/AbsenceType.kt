package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class AbsenceType: Parcelable {
    VACATION,
    BUSINESS_TRIP,
    SICK_LEAVE,
    DAY_OFF
}