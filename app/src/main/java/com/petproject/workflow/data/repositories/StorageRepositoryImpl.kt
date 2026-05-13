package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.StorageApiService
import com.petproject.workflow.data.network.mappers.StorageProductMapper
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.repositories.StorageRepository
import java.io.IOException
import javax.inject.Inject

class StorageRepositoryImpl @Inject constructor(
    private val storageProductMapper: StorageProductMapper,
    private val storageApiService: StorageApiService
): StorageRepository {

    override suspend fun getAllStorages(): List<Storage> {
        val response = storageApiService.getAllStorages()
        return response.map { storageProductMapper.mapDtoToEntity(it) }
    }

    override suspend fun getStorageById(id: String): Storage {
        val response = storageApiService.getStorageById(id)
        if (response.isSuccessful) {
            response.body()?.let { return storageProductMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}