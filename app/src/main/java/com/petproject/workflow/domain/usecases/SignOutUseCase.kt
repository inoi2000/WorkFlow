package com.petproject.workflow.domain.usecases

import com.petproject.workflow.domain.repositories.AuthorizationRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthorizationRepository
) {
    suspend operator fun invoke() = repository.signOut()
}