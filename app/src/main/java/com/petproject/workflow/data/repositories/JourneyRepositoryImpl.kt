package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.JourneyApiService
import com.petproject.workflow.data.network.mappers.StatementJourneyMapper
import com.petproject.workflow.data.network.models.DateTimeUpdateDto
import com.petproject.workflow.data.network.utils.DataHelper
import com.petproject.workflow.domain.entities.Journey
import com.petproject.workflow.domain.repositories.JourneyRepository
import java.io.IOException
import java.time.LocalDateTime
import javax.inject.Inject

class JourneyRepositoryImpl @Inject constructor(
    private val dataHelper: DataHelper,
    private val statementJourneyMapper: StatementJourneyMapper,
    private val journeyApiService: JourneyApiService
): JourneyRepository {

    override suspend fun getAllJourneys(): List<Journey> {
        val response = journeyApiService.getAllJourneys()
        return response.map { statementJourneyMapper.mapDtoToEntity(it) }
    }

    override suspend fun getJourneyById(id: String): Journey {
        val response = journeyApiService.getJourneyById(id)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun getAllCurrentJourneys(): List<Journey> {
        val employeeId = dataHelper.getCurrentEmployeeIdOrAuthException()
        val response = journeyApiService.getJourneysByDriverId(employeeId)
        return response.map { statementJourneyMapper.mapDtoToEntity(it) }
    }

    override suspend fun getAllJourneysByCarId(carId: String): List<Journey> {
        val response = journeyApiService.getJourneysByCarId(carId)
        return response.map { statementJourneyMapper.mapDtoToEntity(it) }
    }

    override suspend fun confirmJourney(journeyId: String, dateTime: LocalDateTime): Journey {
        val dateTimeUpdateDto = DateTimeUpdateDto(journeyId, dateTime.toString())
        val response = journeyApiService.confirmJourney(dateTimeUpdateDto)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun startJourney(journeyId: String, dateTime: LocalDateTime): Journey {
        val dateTimeUpdateDto = DateTimeUpdateDto(journeyId, dateTime.toString())
        val response = journeyApiService.startJourney(dateTimeUpdateDto)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun finishJourney(journeyId: String, dateTime: LocalDateTime): Journey {
        val dateTimeUpdateDto = DateTimeUpdateDto(journeyId, dateTime.toString())
        val response = journeyApiService.finishJourney(dateTimeUpdateDto)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }

    override suspend fun cancelJourney(journeyId: String, dateTime: LocalDateTime): Journey {
        val dateTimeUpdateDto = DateTimeUpdateDto(journeyId, dateTime.toString())
        val response = journeyApiService.cancelJourney(dateTimeUpdateDto)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}