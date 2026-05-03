package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Waste(
    val id: String,
    val name: String,
    val isRecycle: Boolean,
    val allowedStorages: List<Storage>?
) : Parcelable