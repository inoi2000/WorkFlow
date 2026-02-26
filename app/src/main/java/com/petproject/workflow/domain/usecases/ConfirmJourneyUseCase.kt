package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.JourneyRepository
import java.time.LocalDateTime
import javax.inject.Inject

data class ConfirmJourneyUseCase @Inject constructor(
    private val repository: JourneyRepository
) {
    suspend operator fun invoke(journeyId: String, dateTime: LocalDateTime) =
        repository.confirmJourney(journeyId, dateTime)
}