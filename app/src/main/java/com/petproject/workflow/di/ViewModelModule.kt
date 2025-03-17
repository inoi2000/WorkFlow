package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.AccountViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutingTaskListViewModel
import com.petproject.workflow.presentation.viewmodels.LoginViewModel
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
    @ViewModelKey(ExecutingTaskListViewModel::class)
    @Binds
    fun bindExecutingTaskListViewModel(impl: ExecutingTaskListViewModel): ViewModel
}