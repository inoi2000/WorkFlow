package com.petproject.workflow.data.repositories

import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import com.petproject.workflow.data.auth.AppAuth
import com.petproject.workflow.data.network.utils.TokensManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@ApplicationScope
class AuthorizationRepositoryImpl @Inject constructor(
    private val tokensManager: TokensManager,
    private val authService: AuthorizationService
) : AuthorizationRepository {

    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

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
                    val tokens = AppAuth.performTokenRequestSuspend(authService, tokenExchangeRequest)
                    //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
                    tokensManager.saveTokens(tokens)

                    verifySuccessAuthorizationCallback?.invoke(
                        TokensManager.getIdFromAccessToken(tokens.accessToken)
                    )
                    Log.d("Oauth", "6. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
                    onSuccessListener()
                } catch (ex: Exception) {
                    onFailureListener(ex)
                }
            }
        }
    }

    override suspend fun signIn(
        onOpenLoginPage: (Intent) -> Unit,
        onSuccessListener: () -> Unit,
        onFailureListener: (Exception) -> Unit
    ) {
    }

    override suspend fun signOut() {
        tokensManager.deleteToken()
    }

    override suspend fun verifySuccessAuthorization(callback: (String?) -> Unit) {
        verifySuccessAuthorizationCallback = callback
        val token = tokensManager.getAccessToken()
        token?.let {
            val id = TokensManager.getIdFromAccessToken(it)
            callback(id)
        }
    }

    override suspend fun getRole() : Role {
        val token = tokensManager.getAccessToken()
        token?.let {
            val role = TokensManager.getRoleFromAccessToken(it)
            return role
        }
        throw NullPointerException("The role not found")
    }
}