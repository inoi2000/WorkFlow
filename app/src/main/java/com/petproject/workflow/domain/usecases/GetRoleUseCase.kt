package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class GetRoleUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke() = repository.getRole()
}