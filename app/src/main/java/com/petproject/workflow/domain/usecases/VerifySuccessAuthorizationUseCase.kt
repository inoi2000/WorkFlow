package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository

class VerifySuccessAuthorizationUseCase(private val repository: AuthorizationRepository) {
    suspend operator fun invoke(callback: (String?) -> Unit) =
        repository.verifySuccessAuthorization { callback(it) }
}