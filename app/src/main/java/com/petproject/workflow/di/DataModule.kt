package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.TaskApiService
import com.petproject.workflow.data.network.utils.AuthFailedInterceptor
import com.petproject.workflow.data.network.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class DataModule {

    @Provides
    fun provideEmployeeApiService(apiFactory: ApiFactory): EmployeeApiService {
        return apiFactory.employeeApiService
    }

    @Provides
    fun provideTaskApiService(apiFactory: ApiFactory): TaskApiService {
        return apiFactory.taskApiService
    }

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authFailedInterceptor: AuthFailedInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(authFailedInterceptor)
            .build()
    }

    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }
}