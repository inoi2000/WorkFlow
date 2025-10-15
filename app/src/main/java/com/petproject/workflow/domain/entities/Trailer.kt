package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Trailer(
    val id: String = "",
    val brand: String,
    val licensePlate: String,
    val volumeLiter: Double,
    val material: String
) : Parcelable