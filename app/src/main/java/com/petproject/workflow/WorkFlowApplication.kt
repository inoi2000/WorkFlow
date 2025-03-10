package com.petproject.workflow

import android.app.Application
import com.petproject.workflow.di.DaggerApplicationComponent

class WorkFlowApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}