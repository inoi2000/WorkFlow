package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.presentation.views.AccountFragment
import com.petproject.workflow.presentation.views.ExecutorTaskListFragment
import com.petproject.workflow.presentation.views.LoginActivity
import com.petproject.workflow.presentation.views.NewsFragment
import com.petproject.workflow.presentation.views.AbsenceListFragment
import com.petproject.workflow.presentation.views.InspectorTaskListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: LoginActivity)

    fun inject(accountFragment: AccountFragment)

    fun inject(newsFragment: NewsFragment)

    fun inject(absenceListFragment: AbsenceListFragment)

    fun inject(executorTaskListFragment: ExecutorTaskListFragment)

    fun inject(inspectorTaskFragment: InspectorTaskListFragment)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}