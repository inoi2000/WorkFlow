package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.EmployeeDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EmployeeApiService {
    @GET("api/employees/{id}")
    suspend fun getEmployee(
        @Path("id") employeeId: String
    ): Response<EmployeeDto>

    @GET("api/employees/search/{query}")
    suspend fun getAllEmployeesByQuery(
        @Path("query") query: String
    ): List<EmployeeDto>

    @GET("api/employees/subordinate/{id}")
    suspend fun getSubordinateEmployees(
        @Path("id") employeeId: String
    ): List<EmployeeDto>

    @GET("api/employees/{id}/photo")
    suspend fun getEmployeePhoto(
        @Path("id") employeeId: String
    ): Response<ResponseBody>

    @POST("/api/employees/batch")
    suspend fun getBatchEmployees(
        @Body ids: Set<String>
    ): List<EmployeeDto>
}