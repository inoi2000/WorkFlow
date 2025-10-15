package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.TrailerRepository
import javax.inject.Inject

class GetTrailerByIdUseCase @Inject constructor(
    private val repository: TrailerRepository
) {
    suspend operator fun invoke(id: String) = repository.getTrailerById(id)
}