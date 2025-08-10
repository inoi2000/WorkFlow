package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Position(
    val id: String,
    val name: String,
    val level: Int
) : Parcelable
