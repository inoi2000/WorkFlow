package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class EmployeeInfoViewModel @Inject constructor(
    private val employeeId: String,
): ViewModel() {
}