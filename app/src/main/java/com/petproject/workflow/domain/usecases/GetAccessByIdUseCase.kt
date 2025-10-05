package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AccessRepository
import javax.inject.Inject

data class GetAccessByIdUseCase @Inject constructor(
    private val repository: AccessRepository
) {
    suspend operator fun invoke(accessId: String) = repository.getAccessById(accessId)
}