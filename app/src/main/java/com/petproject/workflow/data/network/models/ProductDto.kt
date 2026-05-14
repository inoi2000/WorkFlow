package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.ProductStatus
import com.petproject.workflow.domain.entities.ProductType


data class ProductDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("density")
    @Expose
    val density: Double,
    @SerializedName("status")
    @Expose
    val status: ProductStatus,
    @SerializedName("type")
    @Expose
    val type: ProductType,
    @SerializedName("allowed_storages")
    @Expose
    val allowedStorages: List<StorageDto>?
)