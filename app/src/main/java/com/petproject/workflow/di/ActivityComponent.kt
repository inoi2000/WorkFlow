package com.petproject.workflow.di

import com.petproject.workflow.presentation.views.CarInfoFragment
import com.petproject.workflow.presentation.views.CreateTaskCommentFragment
import com.petproject.workflow.presentation.views.EmployeeInfoFragment
import com.petproject.workflow.presentation.views.ExecutorTaskInfoFragment
import com.petproject.workflow.presentation.views.HomeFragment
import com.petproject.workflow.presentation.views.InspectorTaskInfoFragment
import com.petproject.workflow.presentation.views.JourneyInfoFragment
import com.petproject.workflow.presentation.views.SearchFragment
import com.petproject.workflow.presentation.views.StatementInfoFragment
import com.petproject.workflow.presentation.views.TaskCommentListFragment
import com.petproject.workflow.presentation.views.TrailerInfoFragment
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent(modules = [ActivityViewModelModule::class])
interface ActivityComponent {

    fun inject(homeFragment: HomeFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(executorTaskInfoFragment: ExecutorTaskInfoFragment)

    fun inject(inspectorTaskInfoFragment: InspectorTaskInfoFragment)

    fun inject(journeyInfoFragment: JourneyInfoFragment)

    fun inject(statementInfoFragment: StatementInfoFragment)

    fun inject(carInfoFragment: CarInfoFragment)

    fun inject(trailerInfoFragment: TrailerInfoFragment)

    fun inject(taskCommentListFragment: TaskCommentListFragment)

    fun inject(employeeInfoFragment: EmployeeInfoFragment)

    fun inject(createTaskCommentFragment: CreateTaskCommentFragment)

    @Subcomponent.Factory
    interface Factory {

        fun create(
            @BindsInstance id: String
        ): ActivityComponent
    }
}