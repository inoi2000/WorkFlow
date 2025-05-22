package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.EmployeeRepository
import javax.inject.Inject

class GetAllEmployeesForAssignTaskUseCase @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke() = repository.getAllEmployeesForAssignTask()
}