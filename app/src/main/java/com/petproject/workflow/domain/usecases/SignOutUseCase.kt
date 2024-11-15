package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository

class SignOutUseCase(private val repository: AuthorizationRepository) {
    operator fun invoke() = repository.signOut()
}