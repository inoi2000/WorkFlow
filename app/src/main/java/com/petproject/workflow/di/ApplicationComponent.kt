package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.presentation.views.AccountFragment
import com.petproject.workflow.presentation.views.ExecutorTaskListFragment
import com.petproject.workflow.presentation.views.LoginActivity
import com.petproject.workflow.presentation.views.AnnouncementListFragment
import com.petproject.workflow.presentation.views.AbsenceListFragment
import com.petproject.workflow.presentation.views.CreateTaskAddDetailsFragment
import com.petproject.workflow.presentation.views.CreateTaskDoneFragment
import com.petproject.workflow.presentation.views.CreateTaskSelectionEmployeeFragment
import com.petproject.workflow.presentation.views.InspectorTaskListFragment
import com.petproject.workflow.presentation.views.ServiceListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: LoginActivity)

    fun inject(accountFragment: AccountFragment)

    fun inject(announcementListFragment: AnnouncementListFragment)

    fun inject(serviceListFragment: ServiceListFragment)

    fun inject(absenceListFragment: AbsenceListFragment)

    fun inject(executorTaskListFragment: ExecutorTaskListFragment)

    fun inject(inspectorTaskFragment: InspectorTaskListFragment)

    fun inject(createTaskSelectionEmployeeFragment: CreateTaskSelectionEmployeeFragment)

    fun inject(createTaskAddDetailsFragment: CreateTaskAddDetailsFragment)

    fun inject(createTaskDoneFragment: CreateTaskDoneFragment)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}