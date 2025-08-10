package com.petproject.workflow.data.repositories_test

import android.content.Intent
import com.petproject.workflow.data.network.utils.TokensManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import java.util.UUID
import javax.inject.Inject

@ApplicationScope
class AuthorizationRepositoryImplTest @Inject constructor(
    private val tokensManager: TokensManager,
) : AuthorizationRepository {

    override suspend fun handleAuthResponseIntent(
        intent: Intent,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun startAuthFlow(): Intent {
        TODO("Not yet implemented")
    }

    override fun getLogoutPageIntent(): Intent {
        TODO("Not yet implemented")
    }

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

    override suspend fun signOut() {

    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        verifySuccessAuthorizationCallback = callback
        callback(UUID.randomUUID().toString())
    }

    override suspend fun getRole() : Role {
        return Role.ROLE_DRIVER
    }
}