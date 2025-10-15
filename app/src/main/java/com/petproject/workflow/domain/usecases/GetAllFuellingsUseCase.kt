package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.FuellingRepository
import javax.inject.Inject

class GetAllFuellingsUseCase @Inject constructor(
    private val repository: FuellingRepository
) {
    suspend operator fun invoke() = repository.getAllFuellings()
}