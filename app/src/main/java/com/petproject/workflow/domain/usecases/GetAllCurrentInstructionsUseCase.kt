package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.InstructionRepository
import javax.inject.Inject

data class GetAllCurrentInstructionsUseCase @Inject constructor(
    private val repository: InstructionRepository
) {
    suspend operator fun invoke() = repository.getAllCurrentInstructions()
}