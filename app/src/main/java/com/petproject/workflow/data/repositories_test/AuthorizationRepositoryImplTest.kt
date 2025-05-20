package com.petproject.workflow.data.repositories_test

import com.petproject.workflow.data.network.AuthApiService
import com.petproject.workflow.data.network.models.SignInRequest
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import java.util.UUID
import javax.inject.Inject

@ApplicationScope
class AuthorizationRepositoryImplTest @Inject constructor(
    private val tokenManager: TokenManager,
    private val authApiService: AuthApiService
) : AuthorizationRepository {

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

    override suspend fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit) {
        verifySuccessAuthorizationCallback?.invoke(UUID.randomUUID().toString())
    }

    override suspend fun signOut() {

    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        verifySuccessAuthorizationCallback = callback
        callback(UUID.randomUUID().toString())
    }

    override suspend fun getRole() : Role {
        return Role.DIRECTOR
    }
}