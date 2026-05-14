package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.ProductDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductApiService {

    @GET("api/petroleum-products/")
    suspend fun getAllProducts(): List<ProductDto>

    @GET("api/petroleum-products/{productId}")
    suspend fun getProductById(
        @Path("productId") productId: String
    ): Response<ProductDto>
}