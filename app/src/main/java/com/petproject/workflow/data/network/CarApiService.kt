package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.CarDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CarApiService {

    @GET("api/cars/")
    suspend fun getAllCars(): List<CarDto>

    @GET("api/cars/{carId}")
    suspend fun getCarById(
        @Path("carId") carId: String
    ): CarDto
}