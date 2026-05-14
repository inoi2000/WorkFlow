package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Product

interface ProductRepository {

    suspend fun getAllProducts(): List<Product>

    suspend fun getProductById(id: String): Product
}