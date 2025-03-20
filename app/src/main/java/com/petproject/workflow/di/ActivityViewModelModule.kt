package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutingTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
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
    @ViewModelKey(ExecutingTaskInfoViewModel::class)
    @Binds
    fun bindExecutingTaskInfoViewModel(impl: ExecutingTaskInfoViewModel): ViewModel
}