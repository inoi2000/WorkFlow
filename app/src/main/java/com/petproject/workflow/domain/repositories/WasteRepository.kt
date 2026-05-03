package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Waste

interface WasteRepository {

    suspend fun getAllWastes(): List<Waste>

    suspend fun getWasteById(id: String): Waste
}