package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.presentation.views.AccountFragment
import com.petproject.workflow.presentation.views.ExecutingTaskListFragment
import com.petproject.workflow.presentation.views.LoginActivity
import com.petproject.workflow.presentation.views.NewsFragment
import com.petproject.workflow.presentation.views.VacationListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: LoginActivity)

    fun inject(accountFragment: AccountFragment)

    fun inject(newsFragment: NewsFragment)

    fun inject(vacationListFragment: VacationListFragment)

    fun inject(executingTaskListFragment: ExecutingTaskListFragment)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}