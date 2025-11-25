package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.EmployeeRepository
import javax.inject.Inject

data class LoadEmployeePhoto @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(employeeId: String, callback: (String) -> Unit) =
        repository.loadEmployeePhoto(employeeId, callback)
}