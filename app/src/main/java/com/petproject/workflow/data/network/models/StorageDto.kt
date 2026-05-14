package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.petproject.workflow.domain.entities.StoragePlacement


data class StorageDto(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("total_volume")
    @Expose
    val totalVolume: Double,
    @SerializedName("current_volume")
    @Expose
    val currentVolume: Double,
    @SerializedName("current_product")
    @Expose
    val currentProduct: ProductDto?,
    @SerializedName("allowed_mixing")
    @Expose
    val allowedMixing: Boolean,
    @SerializedName("output_product")
    @Expose
    val outputProduct: ProductDto?,
    @SerializedName("sector")
    @Expose
    val sector: String,
    @SerializedName("placement")
    @Expose
    val placement: StoragePlacement,
    @SerializedName("location")
    @Expose
    val location: String,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String,
    @SerializedName("allowed_products")
    @Expose
    val allowedProducts: List<ProductDto>?
)