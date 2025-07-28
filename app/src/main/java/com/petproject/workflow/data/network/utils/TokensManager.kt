package com.petproject.workflow.data.network.utils

import android.content.Context
import com.auth0.android.jwt.JWT
import com.google.gson.Gson
import com.petproject.workflow.data.auth.TokensModel
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import javax.inject.Inject

@ApplicationScope
class TokensManager @Inject constructor(
    context: Context,
) {

    private val preferences =
        context.getSharedPreferences("WorkFlowApp", Context.MODE_PRIVATE)
    private val editor = preferences.edit()
    private val gson = Gson()

    companion object {
        private const val TOKENS_KEY = "jwt_token"

        fun getIdFromAccessToken(accessToken: String): String? {
            val jwt = JWT(accessToken)
            val id = jwt.getClaim("id").asString()
            return id
        }

        fun getRoleFromAccessToken(accessToken: String): Role {
            val jwt = JWT(accessToken)
            val array = jwt.getClaim("authorities").asArray(String::class.java)
            val roleString = array[0]
            val role = Role.valueOf(roleString)
            return role
        }
    }

    fun getAccessToken(): String? {
        val jsonString = preferences.getString(TOKENS_KEY, null)
        jsonString?.let {
            return mapObjectFromJsonString(it).accessToken
        }
        return null
    }

    fun getRefreshToken(): String? {
        val jsonString = preferences.getString(TOKENS_KEY, null)
        jsonString?.let {
            return mapObjectFromJsonString(it).refreshToken
        }
        return null
    }

    fun getIdToken(): String? {
        val jsonString = preferences.getString(TOKENS_KEY, null)
        jsonString?.let {
            return mapObjectFromJsonString(it).idToken
        }
        return null
    }

    fun saveTokens(token: TokensModel) {
        val jsonString = gson.toJson(token)
        editor.putString(TOKENS_KEY, jsonString).commit()
    }

    fun deleteToken() {
        editor.remove(TOKENS_KEY).commit()
    }

    private fun mapObjectFromJsonString(jsonString: String): TokensModel {
        return gson.fromJson(jsonString, TokensModel::class.java)
    }
}