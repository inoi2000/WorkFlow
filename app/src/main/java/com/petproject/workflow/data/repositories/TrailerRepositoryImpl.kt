package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.mappers.TrailerMapper
import com.petproject.workflow.domain.entities.Trailer
import com.petproject.workflow.domain.repositories.TrailerRepository
import javax.inject.Inject

class TrailerRepositoryImpl @Inject constructor(
    val trailerMapper: TrailerMapper
): TrailerRepository {

    override suspend fun getAllTrailers(): List<Trailer> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrailerById(id: String): Trailer {
        TODO("Not yet implemented")
    }

}