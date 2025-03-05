package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.models.SignInRequest
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import kotlinx.coroutines.runBlocking

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private val tokenManager = TokenManager()
    private val authApiService = ApiFactory.authApiService

    override fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit) {
        runBlocking {
            val signInRequest = SignInRequest(username, password)
            val response = authApiService.signIn(signInRequest)
            if (response.isSuccessful) {
                response.body()?.let {
                    tokenManager.saveToken(it.token)
                }
            } else {
                onFailureListener(Exception(response.message()))
            }
        }
    }

    override fun signOut() {
        tokenManager.deleteToken()
    }

    override fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        val token = tokenManager.getToken()
        token?.let {
            callback(TokenManager.getIdFromToken(it))
        }
    }
}