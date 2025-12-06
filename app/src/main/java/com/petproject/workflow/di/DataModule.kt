package com.petproject.workflow.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.petproject.workflow.data.network.AbsenceApiService
import com.petproject.workflow.data.network.AccessApiService
import com.petproject.workflow.data.network.AnnouncementApiService
import com.petproject.workflow.data.network.ApiFactory
import com.petproject.workflow.data.network.CarApiService
import com.petproject.workflow.data.network.CommentApiService
import com.petproject.workflow.data.network.EmployeeApiService
import com.petproject.workflow.data.network.FuellingApiService
import com.petproject.workflow.data.network.InstructionApiService
import com.petproject.workflow.data.network.JourneyApiService
import com.petproject.workflow.data.network.StatementApiService
import com.petproject.workflow.data.network.TaskApiService
import com.petproject.workflow.data.network.TrailerApiService
import com.petproject.workflow.data.network.utils.AuthFailedInterceptor
import com.petproject.workflow.data.network.utils.AuthInterceptor
import com.petproject.workflow.data.network.utils.FileUploadHelper
import dagger.Module
import dagger.Provides
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream

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

    @Provides
    fun provideCarApiService(apiFactory: ApiFactory): CarApiService {
        return apiFactory.carApiService
    }

    @Provides
    fun provideTrailerApiService(apiFactory: ApiFactory): TrailerApiService {
        return apiFactory.trailerApiService
    }

    @Provides
    fun provideFuellingApiService(apiFactory: ApiFactory): FuellingApiService {
        return apiFactory.fuellingApiService
    }

    @Provides
    fun provideJourneyApiService(apiFactory: ApiFactory): JourneyApiService {
        return apiFactory.journeyApiService
    }

    @Provides
    fun provideStatementApiService(apiFactory: ApiFactory): StatementApiService {
        return apiFactory.statementApiService
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

    @ApplicationScope
    @Provides
    fun provideGlide(
        context: Context,
        okHttpClient: OkHttpClient
    ): RequestManager {
        Glide.get(context).registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
        return Glide.with(context)
    }

    @Provides
    fun provideAuthService(context: Context): AuthorizationService {
        return AuthorizationService(context)
    }

    @Provides
    fun provideFileUploadHelper(
        context: Context
    ): FileUploadHelper {
        return FileUploadHelper(context)
    }
}