package com.petproject.workflow.data.network.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationResponse(
    @SerializedName("token")
    @Expose
    val token: String
)
