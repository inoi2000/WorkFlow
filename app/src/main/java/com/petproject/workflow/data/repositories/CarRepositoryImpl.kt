package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.mappers.CarMapper
import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.repositories.CarRepository
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val carMapper: CarMapper
): CarRepository {
    override suspend fun getAllCars(): List<Car> {
        TODO("Not yet implemented")
    }

    override suspend fun getCarById(id: String): Car {
        TODO("Not yet implemented")
    }
}