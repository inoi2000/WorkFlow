package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Storage(
    val id: String,
    val name: String,
    val totalVolume: Double,
    val currentVolume: Double,
    val currentProduct: Product?,
    val allowedMixing: Boolean,
    val outputProduct: Product?,
    val sector: String,
    val placement: StoragePlacement,
    val location: String,
    val updatedAt: LocalDateTime,
    val allowedProducts: List<Product>?
) : Parcelable