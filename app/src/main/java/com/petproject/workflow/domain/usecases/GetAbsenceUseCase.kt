package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AbsenceRepository
import javax.inject.Inject

class GetAbsenceUseCase @Inject constructor(
    private val repository: AbsenceRepository
) {
    suspend operator fun invoke(id: String) = repository.getAbsence(id)
}