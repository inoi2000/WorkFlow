package com.petproject.workflow.presentation.utils

import android.os.Parcelable
import com.petproject.workflow.domain.entities.Car
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionCarArg(
    val onCarSelected: (Car) -> Unit
): Parcelable