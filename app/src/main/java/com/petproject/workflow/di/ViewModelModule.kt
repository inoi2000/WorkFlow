package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.AbsenceViewModel
import com.petproject.workflow.presentation.viewmodels.AccessListViewModel
import com.petproject.workflow.presentation.viewmodels.AccountViewModel
import com.petproject.workflow.presentation.viewmodels.AnnouncementListViewModel
import com.petproject.workflow.presentation.viewmodels.AuthViewModel
import com.petproject.workflow.presentation.viewmodels.CarListViewModel
import com.petproject.workflow.presentation.viewmodels.CreateAnnouncementViewModel
import com.petproject.workflow.presentation.viewmodels.CreateStatementViewModel
import com.petproject.workflow.presentation.viewmodels.CreateTaskAddDetailsViewModel
import com.petproject.workflow.presentation.viewmodels.CreateTaskDoneViewModel
import com.petproject.workflow.presentation.viewmodels.CreateTaskSelectionEmployeeViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.FuellingListViewModel
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.InstructionListViewModel
import com.petproject.workflow.presentation.viewmodels.JourneyListViewModel
import com.petproject.workflow.presentation.viewmodels.ServiceListViewModel
import com.petproject.workflow.presentation.viewmodels.StatementListViewModel
import com.petproject.workflow.presentation.viewmodels.TrailerListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(AccountViewModel::class)
    @Binds
    fun bindAccountViewModel(impl: AccountViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(impl: AuthViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AnnouncementListViewModel::class)
    @Binds
    fun bindAnnouncementListViewModel(impl: AnnouncementListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ServiceListViewModel::class)
    @Binds
    fun bindServiceListViewModel(impl: ServiceListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ExecutorTaskListViewModel::class)
    @Binds
    fun bindExecutingTaskListViewModel(impl: ExecutorTaskListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InspectorTaskListViewModel::class)
    @Binds
    fun bindInspectorTaskListViewModel(impl: InspectorTaskListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AbsenceViewModel::class)
    @Binds
    fun bindAbsenceViewModel(impl: AbsenceViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CarListViewModel::class)
    @Binds
    fun bindCarListViewModel(impl: CarListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TrailerListViewModel::class)
    @Binds
    fun bindTrailerListViewModel(impl: TrailerListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AccessListViewModel::class)
    @Binds
    fun bindAccessListViewModel(impl: AccessListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InstructionListViewModel::class)
    @Binds
    fun bindInstructionListViewModel(impl: InstructionListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateTaskSelectionEmployeeViewModel::class)
    @Binds
    fun bindCreateTaskSelectionEmployeeViewModel(impl: CreateTaskSelectionEmployeeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateTaskAddDetailsViewModel::class)
    @Binds
    fun bindCreateTaskAddDetailsViewModel(impl: CreateTaskAddDetailsViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateTaskDoneViewModel::class)
    @Binds
    fun bindCreateTaskDoneViewModel(impl: CreateTaskDoneViewModel): ViewModel

    @IntoMap
    @ViewModelKey(FuellingListViewModel::class)
    @Binds
    fun bindFuellingListViewModel(impl: FuellingListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(JourneyListViewModel::class)
    @Binds
    fun bindJourneyListViewModel(impl: JourneyListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(StatementListViewModel::class)
    @Binds
    fun bindStatementListViewModel(impl: StatementListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateAnnouncementViewModel::class)
    @Binds
    fun bindCreateAnnouncementViewModel(impl: CreateAnnouncementViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateStatementViewModel::class)
    @Binds
    fun bindCreateStatementViewModel(impl: CreateStatementViewModel): ViewModel
}