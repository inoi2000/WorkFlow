package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.repositories.StatementRepository
import javax.inject.Inject

class CreateStatementUseCase @Inject constructor(
    private val repository: StatementRepository
) {
    suspend operator fun invoke(statement: Statement) = repository.createStatement(statement)
}