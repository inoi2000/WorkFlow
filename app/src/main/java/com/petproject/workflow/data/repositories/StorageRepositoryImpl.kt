package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.StorageApiService
import com.petproject.workflow.data.network.mappers.StorageProductMapper
import com.petproject.workflow.domain.entities.Product
import com.petproject.workflow.domain.entities.ProductStatus
import com.petproject.workflow.domain.entities.ProductType
import com.petproject.workflow.domain.entities.Storage
import com.petproject.workflow.domain.entities.StoragePlacement
import com.petproject.workflow.domain.repositories.StorageRepository
import java.io.IOException
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject


class StorageRepositoryImpl @Inject constructor(
    private val storageProductMapper: StorageProductMapper,
    private val storageApiService: StorageApiService
): StorageRepository {

    override suspend fun getAllStorages(): List<Storage> {
        val response = storageApiService.getAllStorages()
        return response.map { storageProductMapper.mapDtoToEntity(it) }

//        val heatingOil = Product(
//            UUID.randomUUID().toString(),
//            "Печное топливо",
//            0.86,
//            ProductStatus.LIQUID,
//            ProductType.PURCHASED_FOR_PRODUCTION,
//            ArrayList()
//        )
//
//        val testStorage1 = Storage(
//            UUID.randomUUID().toString(),
//            "E1",
//            40.0,
//            0.0,
//            null,
//            false,
//            null,
//            "56:21:1808001:858",
//            StoragePlacement.ABOVE_GROUND,
//            "Ёмкостной парк",
//            LocalDateTime.now(),
//            listOf()
//        )
//
//        val testStorage2 = Storage(
//            UUID.randomUUID().toString(),
//            "E2",
//            50.0,
//            30.0,
//            heatingOil,
//            false,
//            null,
//            "56:21:1808001:858",
//            StoragePlacement.ABOVE_GROUND,
//            "Пандус",
//            LocalDateTime.now(),
//            listOf()
//        )
//
//        val testStorage3 = Storage(
//            UUID.randomUUID().toString(),
//            "E3",
//            100.0,
//            90.5,
//            heatingOil,
//            false,
//            null,
//            "56:21:1808001:858",
//            StoragePlacement.ABOVE_GROUND,
//            "Пандус",
//            LocalDateTime.now(),
//            listOf()
//        )
//        return listOf(testStorage1, testStorage2, testStorage3)
    }

    override suspend fun getStorageById(id: String): Storage {
        val response = storageApiService.getStorageById(id)
        if (response.isSuccessful) {
            response.body()?.let { return storageProductMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}