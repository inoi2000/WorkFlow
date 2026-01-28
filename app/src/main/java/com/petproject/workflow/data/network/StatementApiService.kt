package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.StatementDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface StatementApiService {

    @GET("api/statements/")
    suspend fun getAllStatements(): List<StatementDto>

    @GET("api/statements/{statementId}")
    suspend fun getStatementById(
        @Path("statementId") statementId: String
    ): Response<StatementDto>

    @POST("api/statements")
    suspend fun createStatement(
        @Body statementDto: StatementDto
    ): Response<StatementDto>
}