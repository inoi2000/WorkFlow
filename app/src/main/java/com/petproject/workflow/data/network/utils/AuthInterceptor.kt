package com.petproject.workflow.data.network.utils

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokensManager: TokensManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokensManager.getAccessToken()
        val request = chain.request().newBuilder()
        request.addHeader("Authorization", "Bearer $token")
        return chain.proceed(request.build())
    }
}