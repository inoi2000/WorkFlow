package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.FuellingApiService
import com.petproject.workflow.data.network.mappers.FuellingMapper
import com.petproject.workflow.domain.entities.Fuelling
import com.petproject.workflow.domain.repositories.FuellingRepository
import javax.inject.Inject

class FuellingRepositoryImpl @Inject constructor(
    private val fuellingMapper: FuellingMapper,
    private val fuellingApiService: FuellingApiService
): FuellingRepository {

    override suspend fun getAllFuellings(): List<Fuelling> {
        val response = fuellingApiService.getAllFuellings()
        return response.map { fuellingMapper.mapDtoToEntity(it) }
    }
}