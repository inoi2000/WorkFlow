package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.FuellingDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FuellingApiService {

    @GET("api/fuellings/")
    suspend fun getAllFuellings(): List<FuellingDto>

    @GET("api/fuellings/{fuellingId}")
    suspend fun getFuellingById(
        @Path("fuellingId") fuellingId: String
    ): FuellingDto
}