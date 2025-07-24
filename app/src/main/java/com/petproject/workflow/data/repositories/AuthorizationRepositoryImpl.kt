package com.petproject.workflow.data.repositories

import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.petproject.workflow.data.auth.AppAuth
import com.petproject.workflow.data.auth.TokenStorage
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

@ApplicationScope
class AuthorizationRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
    private val authService: AuthorizationService
) : AuthorizationRepository {

    override fun startAuthFlow(): Intent {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = AppAuth.getAuthRequest()
        Log.d(
            "Oauth",
            "1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}"
        )
        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
        Log.d(
            "Oauth",
            "2. Open auth page: ${authRequest.toUri()}"
        )
        return openAuthPageIntent
    }

    override suspend fun handleAuthResponseIntent(
        intent: Intent,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> {
                onFailureListener(exception)
            }
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null -> {
                Log.d("Oauth", "3. Received code = ${tokenExchangeRequest.authorizationCode}")
                try {
                    Log.d("Oauth", "4. Change code to token. Url = ${tokenExchangeRequest.configuration.tokenEndpoint}, verifier = ${tokenExchangeRequest.codeVerifier}")
                    performTokenRequest(
                        authService = authService,
                        tokenRequest = tokenExchangeRequest
                    )
                    onSuccessListener()
                } catch (ex: Exception) {
                    onFailureListener(ex)
                }
            }
        }
    }

    private suspend fun performTokenRequest(
        authService: AuthorizationService,
        tokenRequest: TokenRequest,
    ) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        TokenStorage.idToken = tokens.idToken
        Log.d("Oauth", "6. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
    }

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

    override suspend fun signIn(
        onOpenLoginPage: (Intent) -> Unit,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
    }

    override suspend fun signOut() {
        tokenManager.deleteToken()
    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        verifySuccessAuthorizationCallback = callback
        val token = tokenManager.getToken()
        token?.let {
            callback(TokenManager.getIdFromToken(it))
        }
    }

    override suspend fun getRole() : Role {
        TODO("Not yet implemented")
    }
}