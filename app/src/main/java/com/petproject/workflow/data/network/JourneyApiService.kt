package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.JourneyDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface JourneyApiService {

    @GET("api/journeys/")
    suspend fun getAllJourneys(): List<JourneyDto>

    @GET("api/journeys/{journeyId}")
    suspend fun getJourneyById(
        @Path("journeyId") journeyId: String
    ): Response<JourneyDto>

    @GET("api/journeys/drivers/{driverId}")
    suspend fun getJourneysByDriverId(
        @Path("driverId") driverId: String
    ): List<JourneyDto>

    @GET("api/journeys/cars/{carId}")
    suspend fun getJourneysByCarId(
        @Path("carId") carId: String
    ): List<JourneyDto>
}