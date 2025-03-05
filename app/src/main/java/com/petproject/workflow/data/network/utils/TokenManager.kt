package com.petproject.workflow.data.network.utils

import android.content.Context
import com.auth0.android.jwt.JWT
import com.petproject.workflow.presentation.application.WorkFlowApplication

class TokenManager {

    private val context by lazy { WorkFlowApplication.context ?: throw RuntimeException() }

    private val preferences =
        context.getSharedPreferences("WorkFlowApp", Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    companion object {
        private const val TOKEN_KEY = "jwt_token"

        fun getIdFromToken(token: String): String? {
            val jwt: JWT = JWT(token)
            return jwt.getClaim("id").asString()
        }
    }

    fun getToken(): String? {
        return preferences.getString(TOKEN_KEY, null);
    }

    fun saveToken(token: String) {
        editor.putString(TOKEN_KEY, token).commit()
    }

    fun deleteToken() {
        editor.remove(TOKEN_KEY).commit()
    }
}