package com.petproject.workflow.di

import com.petproject.workflow.presentation.views.ExecutingTaskInfoFragment
import com.petproject.workflow.presentation.views.HomeFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ActivityViewModelModule::class])
interface ActivityComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(executingTaskInfoFragment: ExecutingTaskInfoFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance id: String
        ): ActivityComponent
    }
}