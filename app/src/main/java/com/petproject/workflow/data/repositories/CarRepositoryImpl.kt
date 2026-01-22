package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.CarApiService
import com.petproject.workflow.data.network.mappers.CarMapper
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.repositories.CarRepository
import java.io.IOException
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val carMapper: CarMapper,
    private val carApiService: CarApiService
): CarRepository {

    override suspend fun getAllCars(): List<Car> {
        val response = carApiService.getAllCars()
        return response.map { carMapper.mapDtoToEntity(it) }
    }

    override suspend fun getCarById(id: String): Car {
        val response = carApiService.getCarById(id)
        if (response.isSuccessful) {
            response.body()?.let { return  carMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}