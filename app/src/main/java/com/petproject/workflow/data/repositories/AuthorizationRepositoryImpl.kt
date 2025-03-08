package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.models.SignInRequest
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.domain.repositories.AuthorizationRepository

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private val tokenManager = TokenManager()
    private val authApiService = ApiFactory.authApiService

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

    override suspend fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit) {
        val signInRequest = SignInRequest(username, password)
        val response = authApiService.signIn(signInRequest)
        if (response.isSuccessful) {
            response.body()?.token?.let {
                tokenManager.saveToken(it)
                verifySuccessAuthorizationCallback?.invoke(TokenManager.getIdFromToken(it))
            }
        } else {
            onFailureListener(Exception(response.message()))
        }
    }

    override suspend fun signOut() {
        tokenManager.deleteToken()
    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        verifySuccessAuthorizationCallback = callback
        val token = tokenManager.getToken()
        token?.let {
            callback(TokenManager.getIdFromToken(it))
        }
    }
}