package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.AbsenceViewModel
import com.petproject.workflow.presentation.viewmodels.AccountViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.InspectorTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.LoginViewModel
import com.petproject.workflow.presentation.viewmodels.ServiceListViewModel
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
    @ViewModelKey(LoginViewModel::class)
    @Binds
    fun bindLoginViewModel(impl: LoginViewModel): ViewModel

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
}