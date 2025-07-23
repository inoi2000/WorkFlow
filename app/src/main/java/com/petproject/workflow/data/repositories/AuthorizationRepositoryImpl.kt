package com.petproject.workflow.data.repositories

import android.content.Intent
import com.petproject.workflow.data.network.AuthApiService
import com.petproject.workflow.data.network.models.SignInRequest
import com.petproject.workflow.data.network.utils.TokenManager
import com.petproject.workflow.di.ApplicationScope
import com.petproject.workflow.domain.entities.Role
import com.petproject.workflow.domain.repositories.AuthorizationRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import net.openid.appauth.AuthorizationService
import javax.inject.Inject

@ApplicationScope
class AuthorizationRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager,
//        private val authApiService: AuthApiService,
    private val authService: AuthorizationService
) : AuthorizationRepository {

//    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
//    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
//    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)
//
//    private val loadingMutableStateFlow = MutableStateFlow(false)
//
//    val openAuthPageFlow: Flow<Intent>
//        get() = openAuthPageEventChannel.receiveAsFlow()
//
//    val loadingFlow: Flow<Boolean>
//        get() = loadingMutableStateFlow.asStateFlow()
//
//    val toastFlow: Flow<Int>
//        get() = toastEventChannel.receiveAsFlow()
//
//    val authSuccessFlow: Flow<Unit>
//        get() = authSuccessEventChannel.receiveAsFlow()
//
//    fun onAuthCodeFailed(exception: AuthorizationException) {
//        toastEventChannel.trySendBlocking(R.string.auth_canceled)
//    }
//
//    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
//
//        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")
//
//        viewModelScope.launch {
//            loadingMutableStateFlow.value = true
//            runCatching {
//                Timber.tag("Oauth").d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
//                authRepository.performTokenRequest(
//                    authService = authService,
//                    tokenRequest = tokenRequest
//                )
//            }.onSuccess {
//                loadingMutableStateFlow.value = false
//                authSuccessEventChannel.send(Unit)
//            }.onFailure {
//                loadingMutableStateFlow.value = false
//                toastEventChannel.send(R.string.auth_canceled)
//            }
//        }
//    }
//
//    fun openLoginPage() {
//        val customTabsIntent = CustomTabsIntent.Builder().build()
//
//        val authRequest = authRepository.getAuthRequest()
//
//        Timber.tag("Oauth").d("1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")
//
//        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
//            authRequest,
//            customTabsIntent
//        )
//
//        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
//        Timber.tag("Oauth").d("2. Open auth page: ${authRequest.toUri()}")
//    }






    private var verifySuccessAuthorizationCallback: ((String?) -> Unit)? = null

    override suspend fun signIn(onFailureListener: (Exception) -> Unit) {
//        val signInRequest = SignInRequest(username, password)
//        val response = authApiService.signIn(signInRequest)
//        if (response.isSuccessful) {
//            response.body()?.token?.let {
//                tokenManager.saveToken(it)
//                verifySuccessAuthorizationCallback?.invoke(TokenManager.getIdFromToken(it))
//            }
//        } else {
//            onFailureListener(Exception(response.message()))
//        }
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