package com.petproject.workflow.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FileKey(
    val id: String,
    val key: String
) : Parcelable
