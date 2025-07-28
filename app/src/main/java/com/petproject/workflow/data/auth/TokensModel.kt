package com.petproject.workflow.data.auth

data class TokensModel(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)
