package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Journey

interface JourneyRepository {

    suspend fun getAllJourneys(): List<Journey>

    suspend fun getJourneyById(id: String): Journey

    suspend fun getAllCurrentJourneys(): List<Journey>

    suspend fun getAllJourneysByCarId(carId: String): List<Journey>
}