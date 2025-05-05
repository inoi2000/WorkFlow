package com.petproject.workflow.domain.repositories

import com.petproject.workflow.domain.entities.Employee

interface EmployeeRepository {

    suspend fun getEmployee(id: String): Employee

    suspend fun getCurrentEmployee(): Employee
}