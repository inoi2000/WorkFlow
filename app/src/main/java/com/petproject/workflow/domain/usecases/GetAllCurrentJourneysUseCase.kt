package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.JourneyRepository
import javax.inject.Inject

class GetAllCurrentJourneysUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke() = repository.getAllCurrentJourneys()
}