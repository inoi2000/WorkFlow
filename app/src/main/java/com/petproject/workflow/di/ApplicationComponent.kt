package com.petproject.workflow.di

import android.content.Context
import com.petproject.workflow.presentation.views.AccountFragment
import com.petproject.workflow.presentation.views.ExecutorTaskListFragment
import com.petproject.workflow.presentation.views.AuthActivity
import com.petproject.workflow.presentation.views.AnnouncementListFragment
import com.petproject.workflow.presentation.views.AbsenceListFragment
import com.petproject.workflow.presentation.views.AccessListFragment
import com.petproject.workflow.presentation.views.CreateAnnouncementFragment
import com.petproject.workflow.presentation.views.CreateTaskAddDetailsFragment
import com.petproject.workflow.presentation.views.CreateTaskDoneFragment
import com.petproject.workflow.presentation.views.CreateTaskSelectionEmployeeFragment
import com.petproject.workflow.presentation.views.EmployeeInfoFragment
import com.petproject.workflow.presentation.views.FuellingListFragment
import com.petproject.workflow.presentation.views.InspectorTaskListFragment
import com.petproject.workflow.presentation.views.InstructionListFragment
import com.petproject.workflow.presentation.views.JourneyListFragment
import com.petproject.workflow.presentation.views.ServiceListFragment
import com.petproject.workflow.presentation.views.StatementListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DomainModule::class, DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(activity: AuthActivity)

    fun inject(accountFragment: AccountFragment)

    fun inject(announcementListFragment: AnnouncementListFragment)

    fun inject(accessListFragment: AccessListFragment)

    fun inject(instructionListFragment: InstructionListFragment)

    fun inject(serviceListFragment: ServiceListFragment)

    fun inject(absenceListFragment: AbsenceListFragment)

    fun inject(executorTaskListFragment: ExecutorTaskListFragment)

    fun inject(inspectorTaskFragment: InspectorTaskListFragment)

    fun inject(createTaskSelectionEmployeeFragment: CreateTaskSelectionEmployeeFragment)

    fun inject(createTaskAddDetailsFragment: CreateTaskAddDetailsFragment)

    fun inject(createTaskDoneFragment: CreateTaskDoneFragment)

    fun inject(fuellingListFragment: FuellingListFragment)

    fun inject(journeyListFragment: JourneyListFragment)

    fun inject(statementListFragment: StatementListFragment)

    fun inject(createAnnouncementFragment: CreateAnnouncementFragment)

    fun activityComponentFactory(): ActivityComponent.Factory

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}