package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.EmployeeRepository

class LoginUseCase(private val repository: EmployeeRepository) {
    operator fun invoke() = repository.login()
}