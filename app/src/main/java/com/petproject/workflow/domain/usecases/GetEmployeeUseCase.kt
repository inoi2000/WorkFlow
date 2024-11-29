package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.EmployeeRepository

class GetEmployeeUseCase(private val repository: EmployeeRepository) {
    operator fun invoke(id: String) = repository.getEmployee(id)
}