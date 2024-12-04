package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository

class SignInUseCase(private val repository: AuthorizationRepository) {
    operator fun invoke(
        email: String,
        password: String,
        onFailureListener: (Exception) -> Unit
    ) = repository.signIn(email, password, onFailureListener)
}