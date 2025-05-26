package com.petproject.workflow.di

import androidx.lifecycle.ViewModel
import com.petproject.workflow.presentation.viewmodels.AnnouncementViewModel
import com.petproject.workflow.presentation.viewmodels.CreateTaskCommentViewModel
import com.petproject.workflow.presentation.viewmodels.ExecutorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.HomeViewModel
import com.petproject.workflow.presentation.viewmodels.InspectorTaskInfoViewModel
import com.petproject.workflow.presentation.viewmodels.TaskCommentListViewModel
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
    @ViewModelKey(ExecutorTaskInfoViewModel::class)
    @Binds
    fun bindExecutingTaskInfoViewModel(impl: ExecutorTaskInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(InspectorTaskInfoViewModel::class)
    @Binds
    fun bindInspectorTaskInfoViewModel(impl: InspectorTaskInfoViewModel): ViewModel

    @IntoMap
    @ViewModelKey(TaskCommentListViewModel::class)
    @Binds
    fun bindTaskCommentListViewModel(impl: TaskCommentListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(CreateTaskCommentViewModel::class)
    @Binds
    fun bindCreateTaskCommentViewModel(impl: CreateTaskCommentViewModel): ViewModel
}