package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.AbsenceDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface AbsenceApiService {
    @GET("api/absence/{id}")
    suspend fun getAbsence(
        @Path("id") absenceId: String
    ): Response<AbsenceDto>

    @GET("api/absence/employee/{employeeId}")
    suspend fun getAllAbsence(
        @Path("employeeId") employeeId: String
    ): List<AbsenceDto>
}