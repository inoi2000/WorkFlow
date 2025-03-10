package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke(
        username: String,
        password: String,
        onFailureListener: (Exception) -> Unit
    ) = repository.signIn(username, password, onFailureListener)
}