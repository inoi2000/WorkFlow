package com.petproject.workflow.data.network.utils

import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.models.AuthenticationResponse
import com.petproject.workflow.presentation.application.WorkFlowApplication
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator : Authenticator {

    private val tokenManager = TokenManager()

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = tokenManager.getToken();
        return runBlocking {
            val newToken = getNewToken(token)

            if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
                tokenManager.deleteToken()
            }

            newToken.body()?.let {
                tokenManager.saveToken(it.token)
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.token}")
                    .build()
            }
        }
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<AuthenticationResponse> {
//        val loggingInterceptor = HttpLoggingInterceptor()
//        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
//        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://jwt-test-api.onrender.com/api/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .client(okHttpClient) //TODO
//            .build()
//        val service = retrofit.create(AuthApiService::class.java)

//        return ApiFactory.authApiService.refreshToken("Bearer $refreshToken")

        return TODO()
    }
}