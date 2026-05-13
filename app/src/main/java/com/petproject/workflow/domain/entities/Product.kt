package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: String,
    val name: String,
    val density: Double,
    val status: ProductStatus,
    val type: ProductType,
    val allowedStorages: List<Storage>?
) : Parcelable