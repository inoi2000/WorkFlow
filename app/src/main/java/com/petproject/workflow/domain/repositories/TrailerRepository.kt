package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Trailer

interface TrailerRepository {

    suspend fun getAllTrailers(): List<Trailer>

    suspend fun getTrailerById(id: String): Trailer
}