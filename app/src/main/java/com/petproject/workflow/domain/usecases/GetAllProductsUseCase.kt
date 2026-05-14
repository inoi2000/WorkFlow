package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() = repository.getAllProducts()
}