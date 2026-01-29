package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.CarStatus
import com.petproject.workflow.domain.repositories.CarRepository
import javax.inject.Inject

class GetAllCarsByStatusUseCase @Inject constructor(
    private val repository: CarRepository
) {
    suspend operator fun invoke(status: CarStatus) =
        repository.getAllCarsByStatus(status)
}