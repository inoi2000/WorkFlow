package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.StatementApiService
import com.petproject.workflow.data.network.mappers.StatementJourneyMapper
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.repositories.StatementRepository
import java.io.IOException
import javax.inject.Inject

class StatementRepositoryImpl @Inject constructor(
    private val statementJourneyMapper: StatementJourneyMapper,
    private val statementApiService: StatementApiService
): StatementRepository {

    override suspend fun getAllStatements(): List<Statement> {
        val response = statementApiService.getAllStatements()
        return response.map { statementJourneyMapper.mapDtoToEntity(it) }
    }

    override suspend fun getStatementById(id: String): Statement {
        val response = statementApiService.getStatementById(id)
        if (response.isSuccessful) {
            response.body()?.let { return  statementJourneyMapper.mapDtoToEntity(it) }
        }
        throw IOException(response.message())
    }
}