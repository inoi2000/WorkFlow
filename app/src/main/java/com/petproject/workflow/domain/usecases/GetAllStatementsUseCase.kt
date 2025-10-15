package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.StatementRepository
import javax.inject.Inject

class GetAllStatementsUseCase @Inject constructor(
    private val repository: StatementRepository
) {
    suspend operator fun invoke() = repository.getAllStatements()
}