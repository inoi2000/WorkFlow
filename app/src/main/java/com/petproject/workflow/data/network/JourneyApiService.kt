package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.DateTimeUpdateDto
import com.petproject.workflow.data.network.models.JourneyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("/api/journeys/confirm")
    suspend fun confirmJourney(
        @Body dateTimeUpdateDto: DateTimeUpdateDto
    ): Response<JourneyDto>

    @POST("/api/journeys/start")
    suspend fun startJourney(
        @Body dateTimeUpdateDto: DateTimeUpdateDto
    ): Response<JourneyDto>

    @POST("/api/journeys/finish")
    suspend fun finishJourney(
        @Body dateTimeUpdateDto: DateTimeUpdateDto
    ): Response<JourneyDto>

    @POST("/api/journeys/cancel")
    suspend fun cancelJourney(
        @Body dateTimeUpdateDto: DateTimeUpdateDto
    ): Response<JourneyDto>
}