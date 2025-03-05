package com.petproject.workflow.presentation.application

import android.app.Application
import android.content.Context

class WorkFlowApplication: Application() {

    companion object {
        var context: Context? = null
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}