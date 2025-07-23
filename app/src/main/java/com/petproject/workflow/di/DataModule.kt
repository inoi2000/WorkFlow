package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.WorkFlowApplication
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.AuthApiService
import com.petproject.workflow.data.network.MainApiService
import com.petproject.workflow.data.network.utils.AuthInterceptor
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class DataModule {

    @Provides
    fun provideAuthApiService(apiFactory: ApiFactory): AuthApiService {
        return apiFactory.authApiService
    }

    @Provides
    fun provideMainApiService(apiFactory: ApiFactory): MainApiService {
        return apiFactory.mainApiService
    }

    @ApplicationScope
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
//        authAuthenticator: AuthAuthenticator,
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
//            .authenticator(authAuthenticator)
            .build()
    }

    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }
}