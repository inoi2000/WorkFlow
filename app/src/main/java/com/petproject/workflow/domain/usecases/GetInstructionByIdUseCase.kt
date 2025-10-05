package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.InstructionRepository
import javax.inject.Inject

data class GetInstructionByIdUseCase @Inject constructor(
    private val repository: InstructionRepository
) {
    suspend operator fun invoke(instructionId: String) =
        repository.getInstructionById(instructionId)
}