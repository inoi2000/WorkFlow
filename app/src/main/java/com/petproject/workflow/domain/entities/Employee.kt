package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Employee (
    val id: String,
    val name: String,
    val phone: String,
    val email: String,
    val position: Position? = null,
    val department: Department? = null,
) : Parcelable