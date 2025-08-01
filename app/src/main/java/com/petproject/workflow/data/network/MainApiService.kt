package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.EmployeeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MainApiService {
    @GET("api/employees/{id}")
    suspend fun getEmployee(
        @Path("id") employeeId: String
    ): Response<EmployeeDto>
}