package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.WasteApiService
import com.petproject.workflow.data.network.mappers.StorageWasteMapper
import com.petproject.workflow.domain.entities.Waste
import com.petproject.workflow.domain.repositories.WasteRepository
import java.io.IOException
import javax.inject.Inject

class WasteRepositoryImpl @Inject constructor(
    private val storageWasteMapper: StorageWasteMapper,
    private val wasteApiService: WasteApiService
): WasteRepository {

    override suspend fun getAllWastes(): List<Waste> {
        val response = wasteApiService.getAllWastes()
        return response.map { storageWasteMapper.mapDtoToEntity(it) }
    }

    override suspend fun getWasteById(id: String): Waste {
        val response = wasteApiService.getWasteById(id)
        if (response.isSuccessful) {
            response.body()?.let { return storageWasteMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}