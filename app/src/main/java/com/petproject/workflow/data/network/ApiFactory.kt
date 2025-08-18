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
        private const val BASE_EMPLOYEE_API = "http://192.168.0.159:8080/"
        private const val BASE_TASK_API = "http://192.168.0.159:9200/"
        private const val BASE_COMMENT_API = "http://192.168.0.159:9200/"
    }

    val employeeApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_EMPLOYEE_API)
        .client(okHttpClient)
        .build()
        .create(EmployeeApiService::class.java)

    val taskApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_TASK_API)
        .client(okHttpClient)
        .build()
        .create(TaskApiService::class.java)

    val commentApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_COMMENT_API)
        .client(okHttpClient)
        .build()
        .create(CommentApiService::class.java)
}