package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AccessRepository
import javax.inject.Inject

data class GetAllCurrentAccessesUseCase @Inject constructor(
    private val repository: AccessRepository
) {
    suspend operator fun invoke() = repository.getAllCurrentAccesses()
}