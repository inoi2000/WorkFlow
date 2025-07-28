package com.petproject.workflow.data.auth

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://192.168.0.159:9000/oauth2/authorize"
    const val TOKEN_URI = "https://192.168.0.159:9000/oauth2/token"
    const val END_SESSION_URI = "https://192.168.0.159:9000/connect/logout"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "openid"

    const val CLIENT_ID = "client"
    const val CLIENT_SECRET = "secret"
    const val CALLBACK_URL = "com.petproject.workflow://oauth2callback"
    const val LOGOUT_CALLBACK_URL = "com.petproject.workflow://logoutcallback"
}