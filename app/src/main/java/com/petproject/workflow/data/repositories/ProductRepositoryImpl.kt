package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.ProductApiService
import com.petproject.workflow.data.network.mappers.StorageProductMapper
import com.petproject.workflow.domain.entities.Product
import com.petproject.workflow.domain.repositories.ProductRepository
import java.io.IOException
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val storageProductMapper: StorageProductMapper,
    private val productApiService: ProductApiService
): ProductRepository {

    override suspend fun getAllProducts(): List<Product> {
        val response = productApiService.getAllProducts()
        return response.map { storageProductMapper.mapDtoToEntity(it) }
    }

    override suspend fun getProductById(id: String): Product {
        val response = productApiService.getProductById(id)
        if (response.isSuccessful) {
            response.body()?.let { return storageProductMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}