package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.models.AuthenticationResponse
import com.petproject.workflow.data.network.models.SignInRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/sign-in")
    suspend fun signIn(
        @Body signInRequest: SignInRequest,
    ): Response<AuthenticationResponse>

//    @GET("auth/refresh")
//    suspend fun refreshToken(
//        @Header("Authorization") token: String,
//    ): Response<AuthenticationResponse>
}