package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.WasteDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WasteApiService {

    @GET("api/wastes/")
    suspend fun getAllWastes(): List<WasteDto>

    @GET("api/wastes/{wasteId}")
    suspend fun getWasteById(
        @Path("wasteId") wasteId: String
    ): Response<WasteDto>
}