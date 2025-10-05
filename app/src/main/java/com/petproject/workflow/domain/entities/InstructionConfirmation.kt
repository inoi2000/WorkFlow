package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class InstructionConfirmation(
    val employeeId: String = "",
    val isConfirmed: Boolean,
    val confirmedAt: LocalDateTime?
) : Parcelable