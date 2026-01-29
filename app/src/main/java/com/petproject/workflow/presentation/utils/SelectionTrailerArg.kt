package com.petproject.workflow.presentation.utils

import android.os.Parcelable
import com.petproject.workflow.domain.entities.Trailer
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectionTrailerArg(
    val onTrailerSelected: (Trailer) -> Unit
): Parcelable