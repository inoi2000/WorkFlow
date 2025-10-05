package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AccessRepository
import javax.inject.Inject

data class GetAllAccessesByIssuerIdUseCase @Inject constructor(
    private val repository: AccessRepository
) {
    suspend operator fun invoke(issuerId: String) = repository.getAllAccessesByIssuerId(issuerId)
}