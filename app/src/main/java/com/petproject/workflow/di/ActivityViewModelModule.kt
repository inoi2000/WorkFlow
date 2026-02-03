package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.CarInfoViewModel
import com.petproject.workflow.presentation.viewmodels.CreateTaskCommentViewModel
import com.petproject.workflow.presentation.viewmodels.EmployeeInfoViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.InspectorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.JourneyInfoViewModel
import com.petproject.workflow.presentation.viewmodels.SearchViewModel
import com.petproject.workflow.presentation.viewmodels.StatementInfoViewModel
import com.petproject.workflow.presentation.viewmodels.TaskCommentListViewModel
import com.petproject.workflow.presentation.viewmodels.TrailerInfoViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ActivityViewModelModule {

    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    @Binds
    fun bindHomeViewModel(impl: HomeViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    @Binds
    fun bindSearchViewModel(impl: SearchViewModel): ViewModel

    @IntoMap
    @ViewModelKey(ExecutorTaskInfoViewModel::class)
    @Binds
    fun bindExecutingTaskInfoViewModel(impl: ExecutorTaskInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InspectorTaskInfoViewModel::class)
    @Binds
    fun bindInspectorTaskInfoViewModel(impl: InspectorTaskInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(StatementInfoViewModel::class)
    @Binds
    fun bindStatementInfoViewModel(impl: StatementInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(JourneyInfoViewModel::class)
    @Binds
    fun bindJourneyInfoViewModel(impl: JourneyInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CarInfoViewModel::class)
    @Binds
    fun bindCarInfoViewModel(impl: CarInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TrailerInfoViewModel::class)
    @Binds
    fun bindTrailerInfoViewModel(impl: TrailerInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TaskCommentListViewModel::class)
    @Binds
    fun bindTaskCommentListViewModel(impl: TaskCommentListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateTaskCommentViewModel::class)
    @Binds
    fun bindCreateTaskCommentViewModel(impl: CreateTaskCommentViewModel): ViewModel

    @IntoMap
    @ViewModelKey(EmployeeInfoViewModel::class)
    @Binds
    fun bindEmployeeInfoViewModel(impl: EmployeeInfoViewModel): ViewModel

}