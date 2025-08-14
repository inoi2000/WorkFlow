package com.petproject.workflow.data.network.utils

import com.petproject.workflow.data.network.exceptions.AuthException
import javax.inject.Inject

class DataHelper @Inject constructor(
    private val tokensManager: TokensManager,
) {
    fun getCurrentEmployeeIdOrAuthException(): String {
        val accessToken = tokensManager.getAccessToken()
        accessToken?.let { token ->
            val employeeId = TokensManager.getIdFromAccessToken(token)
            employeeId?.let { id ->
                return id
            }
        }
        throw AuthException()
    }
}