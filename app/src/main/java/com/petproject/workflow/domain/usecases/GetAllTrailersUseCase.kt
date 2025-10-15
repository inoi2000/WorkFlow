package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.TrailerRepository
import javax.inject.Inject

class GetAllTrailersUseCase @Inject constructor(
    private val repository: TrailerRepository
) {
    suspend operator fun invoke() = repository.getAllTrailers()
}