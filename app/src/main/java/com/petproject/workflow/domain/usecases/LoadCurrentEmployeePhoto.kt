package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.EmployeeRepository
import javax.inject.Inject

data class LoadCurrentEmployeePhoto @Inject constructor(
    private val repository: EmployeeRepository
) {
    suspend operator fun invoke(callback: (String) -> Unit) = repository.loadCurrentEmployeePhoto(callback)
}