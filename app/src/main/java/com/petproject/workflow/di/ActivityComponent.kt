package com.petproject.workflow.di

import com.petproject.workflow.presentation.views.HomeFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ActivityViewModelModule::class])
interface ActivityComponent {

    fun inject(homeFragment: HomeFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance id: String
        ): ActivityComponent
    }
}