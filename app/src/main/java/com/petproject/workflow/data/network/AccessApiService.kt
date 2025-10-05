package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.AccessDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AccessApiService {
    @GET("api/accesses/{id}")
    suspend fun getAccessById(
        @Path("id") id: String
    ): Response<AccessDto>

    @GET("api/accesses/issuer/{issuerId}")
    suspend fun getAllAccessesByIssuerId(
        @Path("issuerId") issuerId: String
    ): List<AccessDto>

    @GET("/api/accesses/holder/{holderId}")
    suspend fun getAllAccessesByHolderId(
        @Path("holderId") holderId: String
    ): List<AccessDto>
}