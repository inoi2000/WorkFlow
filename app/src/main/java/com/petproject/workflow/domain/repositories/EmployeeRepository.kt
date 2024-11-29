package com.petproject.workflow.domain.repositories

import androidx.lifecycle.LiveData
import com.petproject.workflow.domain.entities.Employee

interface EmployeeRepository {
    fun getEmployee(id: String): LiveData<Employee>
}