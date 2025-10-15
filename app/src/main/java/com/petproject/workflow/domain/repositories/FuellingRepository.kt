package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Fuelling

interface FuellingRepository {

    suspend fun getAllFuellings(): List<Fuelling>
}