package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.mappers.StatementJourneyMapper
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.repositories.JourneyRepository
import javax.inject.Inject

class JourneyRepositoryImpl @Inject constructor(
    private val statementJourneyMapper: StatementJourneyMapper
): JourneyRepository {

    override suspend fun getAllJourneys(): List<Journey> {
        TODO("Not yet implemented")
    }

    override suspend fun getJourneyById(id: String): Journey {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCurrentJourneys(): List<Journey> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllJourneysByCarId(carId: String): List<Journey> {
        TODO("Not yet implemented")
    }
}