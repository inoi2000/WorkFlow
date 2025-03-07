package com.petproject.workflow.data.repositories

import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.models.SignInRequest
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.domain.repositories.AuthorizationRepository

class AuthorizationRepositoryImpl : AuthorizationRepository {

    private val tokenManager = TokenManager()
    private val authApiService = ApiFactory.authApiService

    override suspend fun signIn(username: String, password: String, onFailureListener: (Exception) -> Unit) {
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

    override suspend fun signOut() {
        tokenManager.deleteToken()
    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        val token = tokenManager.getToken()
        //TODO добавить запрос валидности токена
        token?.let {
            callback(TokenManager.getIdFromToken(it))
        }
    }
}