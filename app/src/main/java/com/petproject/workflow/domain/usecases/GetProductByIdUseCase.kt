package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository
) {
    suspend operator fun invoke(id: String) = repository.getProductById(id)
}