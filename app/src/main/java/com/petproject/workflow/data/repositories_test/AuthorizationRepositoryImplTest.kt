package com.petproject.workflow.data.repositories_test

import android.content.Intent
import com.petproject.workflow.data.network.AuthApiService
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

    override suspend fun signIn(
        onOpenLoginPage: (Intent) -> Unit,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

//    override suspend fun signIn(onFailureListener: (Exception) -> Unit) {
//        verifySuccessAuthorizationCallback?.invoke(UUID.randomUUID().toString())
//    }


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