package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.mappers.StatementJourneyMapper
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.repositories.StatementRepository
import javax.inject.Inject

class StatementRepositoryImpl @Inject constructor(
    private val statementJourneyMapper: StatementJourneyMapper
): StatementRepository {

    override suspend fun getAllStatements(): List<Statement> {
        TODO("Not yet implemented")
    }

    override suspend fun getStatementById(id: String): Statement {
        TODO("Not yet implemented")
    }
}