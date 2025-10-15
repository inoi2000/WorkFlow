package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.CarRepository
import javax.inject.Inject

class GetCarByIdUseCase @Inject constructor (
    private val repository: CarRepository
) {
    suspend operator fun invoke(id: String) = repository.getCarById(id)
}