package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.TrailerApiService
import com.petproject.workflow.data.network.mappers.TrailerMapper
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.repositories.TrailerRepository
import java.io.IOException
import javax.inject.Inject

class TrailerRepositoryImpl @Inject constructor(
    private val trailerMapper: TrailerMapper,
    private val trailerApiService: TrailerApiService
): TrailerRepository {

    override suspend fun getAllTrailers(): List<Trailer> {
        val response = trailerApiService.getAllTrailers()
        return response.map { trailerMapper.mapDtoToEntity(it) }
    }

    override suspend fun getTrailerById(id: String): Trailer {
        val response = trailerApiService.getTrailerById(id)
        if (response.isSuccessful) {
            response.body()?.let { return  trailerMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}