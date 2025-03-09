package com.petproject.workflow.data.network

import com.petproject.workflow.data.network.utils.AuthInterceptor
import com.petproject.workflow.di.ApplicationScope
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@ApplicationScope
class ApiFactory @Inject constructor(
    private val authInterceptor: AuthInterceptor
) {

    companion object {
        private const val BASE_API = "http://192.168.0.159:8080/"
    }

    private val okHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
//            .authenticator(AuthAuthenticator())
            .build()
    }

    val authApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API)
        .build()
        .create(AuthApiService::class.java)

    val mainApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_API)
        .client(okHttpClient)
        .build()
        .create(MainApiService::class.java)
}