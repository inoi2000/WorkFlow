package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Car
import com.petproject.workflow.domain.entities.CarStatus

interface CarRepository {

    suspend fun getAllCars(): List<Car>

    suspend fun getAllCarsByStatus(status: CarStatus): List<Car>

    suspend fun getCarById(id: String): Car
}