package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.StorageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface StorageApiService {

    @GET("api/storages/")
    suspend fun getAllStorages(): List<StorageDto>

    @GET("api/storages/{storageId}")
    suspend fun getStorageById(
        @Path("storageId") storageId: String
    ): Response<StorageDto>
}