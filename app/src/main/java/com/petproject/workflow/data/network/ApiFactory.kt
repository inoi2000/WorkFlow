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

    private val apiConfig: ApiConfig = ApiConfig

    val absenceApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_ABSENCE_API)
        .client(okHttpClient)
        .build()
        .create(AbsenceApiService::class.java)

    val employeeApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_EMPLOYEE_API)
        .client(okHttpClient)
        .build()
        .create(EmployeeApiService::class.java)

    val accessApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_ACCESS_API)
        .client(okHttpClient)
        .build()
        .create(AccessApiService::class.java)

    val instructionApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_INSTRUCTION_API)
        .client(okHttpClient)
        .build()
        .create(InstructionApiService::class.java)

    val taskApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_TASK_API)
        .client(okHttpClient)
        .build()
        .create(TaskApiService::class.java)

    val commentApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_COMMENT_API)
        .client(okHttpClient)
        .build()
        .create(CommentApiService::class.java)

    val announcementApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_ANNOUNCEMENT_API)
        .client(okHttpClient)
        .build()
        .create(AnnouncementApiService::class.java)

    val carApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_CAR_API)
        .client(okHttpClient)
        .build()
        .create(CarApiService::class.java)

    val trailerApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_TRAILER_API)
        .client(okHttpClient)
        .build()
        .create(TrailerApiService::class.java)

    val fuellingApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_FUELLING_API)
        .client(okHttpClient)
        .build()
        .create(FuellingApiService::class.java)

    val journeyApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_JOURNEY_API)
        .client(okHttpClient)
        .build()
        .create(JourneyApiService::class.java)

    val statementApiService  = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(apiConfig.BASE_STATEMENT_API)
        .client(okHttpClient)
        .build()
        .create(StatementApiService::class.java)
}