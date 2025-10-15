package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.JourneyRepository
import javax.inject.Inject

class GetJourneyByIdUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(id: String) = repository.getJourneyById(id)
}