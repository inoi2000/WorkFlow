package com.petproject.workflow.data.network

import com.petproject.workflow.di.ApplicationScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@ApplicationScope
class ApiFactory @Inject constructor(
    okHttpClient: OkHttpClient
) {

    companion object {
        private const val BASE_API = "http://192.168.0.159:8080/"
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