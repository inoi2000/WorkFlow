package com.petproject.workflow.data.network.models

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InstructionConfirmationDto(
    @SerializedName("employee_id")
    @Expose
    val employeeId: String = "",
    @SerializedName("is_confirmed")
    @Expose
    val isConfirmed: Boolean,
    @SerializedName("confirmed_at")
    @Expose
    val confirmedAt: String? = null
) : Parcelable