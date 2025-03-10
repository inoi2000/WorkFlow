package com.petproject.workflow.di

import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.AuthApiService
import com.petproject.workflow.data.network.MainApiService
import dagger.Module
import dagger.Provides

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
}