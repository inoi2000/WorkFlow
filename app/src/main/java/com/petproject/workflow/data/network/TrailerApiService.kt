package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.TrailerDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TrailerApiService {

    @GET("api/trailers/")
    suspend fun getAllTrailers(): List<TrailerDto>

    @GET("api/trailers/{trailerId}")
    suspend fun getTrailerById(
        @Path("trailerId") trailerId: String
    ): Response<TrailerDto>
}