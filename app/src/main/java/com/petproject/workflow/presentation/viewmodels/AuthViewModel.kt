package com.petproject.workflow.presentation.viewmodels

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.R
import com.petproject.workflow.data.auth.AuthRepository
import com.petproject.workflow.domain.usecases.SignInUseCase
import com.petproject.workflow.domain.usecases.VerifySuccessAuthorizationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val verifySuccessAuthorizationUseCase: VerifySuccessAuthorizationUseCase,
    private val authRepository: AuthRepository,
    private val authService: AuthorizationService
) : ViewModel() {

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()

//    fun onAuthCodeFailed(exception: AuthorizationException) {
//        toastEventChannel.trySendBlocking(R.string.auth_canceled)
//    }

//    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
//        viewModelScope.launch {
//            Log.d("Oauth", "3. Received code = ${tokenRequest.authorizationCode}")
//            try {
//                Log.d("Oauth", "4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
//                authRepository.performTokenRequest(
//                    authService = authService,
//                    tokenRequest = tokenRequest
//                )
//                authSuccessEventChannel.send(Unit)
//            } catch (ex: Exception) {
//                toastEventChannel.send(R.string.auth_canceled)
//            }
//        }
//    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()

        val authRequest = authRepository.getAuthRequest()

        Log.d("Oauth", "1. Generated verifier=${authRequest.codeVerifier},challenge=${authRequest.codeVerifierChallenge}")

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
        Log.d("Oauth", "2. Open auth page: ${authRequest.toUri()}")
    }

    fun handleAuthResponseIntent(intent: Intent) {
        // пытаемся получить ошибку из ответа. null - если все ок
        val exception = AuthorizationException.fromIntent(intent)
        // пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)
            ?.createTokenExchangeRequest()
        when {
            // авторизация завершались ошибкой
            exception != null -> {
                toastEventChannel.trySendBlocking(R.string.auth_canceled)
            }
            // авторизация прошла успешно, меняем код на токен
            tokenExchangeRequest != null -> {
                viewModelScope.launch {
                    Log.d("Oauth", "3. Received code = ${tokenExchangeRequest.authorizationCode}")
                    try {
                        Log.d("Oauth", "4. Change code to token. Url = ${tokenExchangeRequest.configuration.tokenEndpoint}, verifier = ${tokenExchangeRequest.codeVerifier}")
                        authRepository.performTokenRequest(
                            authService = authService,
                            tokenRequest = tokenExchangeRequest
                        )
                        authSuccessEventChannel.send(Unit)
                    } catch (ex: Exception) {
                        toastEventChannel.send(R.string.auth_canceled)
                    }
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }








    private val _navigateToHomeScreen = MutableLiveData<String?>()
    val navigateToHomeScreen: LiveData<String?> get() = _navigateToHomeScreen

//    init {
//        viewModelScope.launch {
//            verifySuccessAuthorizationUseCase.invoke {
//                _navigateToHomeScreen.value = it
//            }
//        }
//    }

//    fun signIn() {
//        viewModelScope.launch {
//            signInUseCase(email, password) {
//                _errorInputEmail.value = true
//                _errorInputPassword.value = true
//            }
//        }
//    }


    fun onHomeScreenNavigated() {
        _navigateToHomeScreen.value = null
    }
}