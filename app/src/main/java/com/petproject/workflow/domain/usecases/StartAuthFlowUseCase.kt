package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class StartAuthFlowUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    operator fun invoke() = repository.startAuthFlow()
}