package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AbsenceRepository
import javax.inject.Inject

class GetAllCurrentAbsenceUseCase @Inject constructor(
    private val repository: AbsenceRepository
) {
    suspend operator fun invoke() = repository.getAllCurrentAbsence()
}