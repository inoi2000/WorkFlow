package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.data.network.AbsenceApiService
import com.petproject.workflow.data.network.AccessApiService
import com.petproject.workflow.data.network.AnnouncementApiService
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.CommentApiService
import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.InstructionApiService
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
    fun provideAccessApiService(apiFactory: ApiFactory): AccessApiService {
        return apiFactory.accessApiService
    }

    @Provides
    fun provideInstructionApiService(apiFactory: ApiFactory): InstructionApiService {
        return apiFactory.instructionApiService
    }

    @Provides
    fun provideTaskApiService(apiFactory: ApiFactory): TaskApiService {
        return apiFactory.taskApiService
    }

    @Provides
    fun provideCommentApiService(apiFactory: ApiFactory): CommentApiService {
        return apiFactory.commentApiService
    }

    @Provides
    fun provideAbsenceApiService(apiFactory: ApiFactory): AbsenceApiService {
        return apiFactory.absenceApiService
    }

    @Provides
    fun provideAnnouncementApiService(apiFactory: ApiFactory): AnnouncementApiService {
        return apiFactory.announcementApiService
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
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(authFailedInterceptor)
            .build()
    }

    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }
}