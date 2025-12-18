package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Approval(
    val id: String,
    val absence: Absence,
    val approver: Employee,
    val description: String
) : Parcelable
