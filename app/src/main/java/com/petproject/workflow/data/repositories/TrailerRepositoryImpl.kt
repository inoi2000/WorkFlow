package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.TrailerApiService
import com.petproject.workflow.data.network.mappers.TrailerMapper
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.repositories.TrailerRepository
import java.io.IOException
import javax.inject.Inject

class TrailerRepositoryImpl @Inject constructor(
    val trailerMapper: TrailerMapper,
    val trailerApiService: TrailerApiService
): TrailerRepository {

    override suspend fun getAllTrailers(): List<Trailer> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailerById(id: String): Trailer {
        TODO("Not yet implemented")
    }

}