package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Department(
    val id: String,
    val name: String,
    val staff: List<Employee>? = null
) : Parcelable