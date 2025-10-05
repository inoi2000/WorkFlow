package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.InstructionDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface InstructionApiService {
    @GET("api/instructions/{id}")
    suspend fun getInstructionById(
        @Path("id") id: String
    ): Response<InstructionDto>

    @GET("api/instructions/employee/{employeeId}")
    suspend fun getAllInstructionsByEmployeeId(
        @Path("employeeId") employeeId: String
    ): List<InstructionDto>
}