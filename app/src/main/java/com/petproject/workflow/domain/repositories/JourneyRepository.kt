package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Journey
import java.time.LocalDateTime

interface JourneyRepository {

    suspend fun getAllJourneys(): List<Journey>

    suspend fun getJourneyById(id: String): Journey

    suspend fun getAllCurrentJourneys(): List<Journey>

    suspend fun getAllJourneysByCarId(carId: String): List<Journey>

    suspend fun confirmJourney(journeyId: String, dateTime: LocalDateTime): Journey

    suspend fun startJourney(journeyId: String, dateTime: LocalDateTime): Journey

    suspend fun finishJourney(journeyId: String, dateTime: LocalDateTime): Journey

    suspend fun cancelJourney(journeyId: String, dateTime: LocalDateTime): Journey
}