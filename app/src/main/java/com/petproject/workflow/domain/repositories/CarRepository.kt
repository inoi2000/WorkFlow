package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Car

interface CarRepository {

    suspend fun getAllCars(): List<Car>

    suspend fun getCarById(id: String): Car
}