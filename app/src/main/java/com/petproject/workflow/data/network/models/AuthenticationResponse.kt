package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("id")
    @Expose
    val id: String,
    @SerializedName("token")
    @Expose
    val token: String
)
