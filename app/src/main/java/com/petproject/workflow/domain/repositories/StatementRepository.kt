package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Statement

interface StatementRepository {

    suspend fun getAllStatements(): List<Statement>

    suspend fun getStatementById(id: String): Statement
}