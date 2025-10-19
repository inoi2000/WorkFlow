package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: String,
    val brand: String,
    val model: String,
    val licensePlate: String,
    val vin: String,
    val year: Int,
    val color: String,
    val odometer: Double,
    val status: CarStatus
): Parcelable