package com.petproject.workflow.presentation.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.R
import com.petproject.workflow.domain.usecases.HandleAuthResponseIntentUseCase
import com.petproject.workflow.domain.usecases.StartAuthFlowUseCase
import com.petproject.workflow.domain.usecases.VerifySuccessAuthorizationUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val startAuthFlowUseCase: StartAuthFlowUseCase,
    private val handleAuthResponseIntentUseCase: HandleAuthResponseIntentUseCase,
    private val verifySuccessAuthorizationUseCase: VerifySuccessAuthorizationUseCase
) : ViewModel() {

    private val _navigateToHomeScreen = MutableLiveData<String?>()
    val navigateToHomeScreen: LiveData<String?> get() = _navigateToHomeScreen

    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()

    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()

    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()

    fun onHomeScreenNavigated() {
        _navigateToHomeScreen.value = null
    }

    init {
        viewModelScope.launch {
            verifySuccessAuthorizationUseCase.invoke {
                _navigateToHomeScreen.value = it
            }
        }
    }

    fun openLoginPage() {
        val openAuthPageIntent = startAuthFlowUseCase()
        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
    }

    fun handleAuthResponseIntent(intent: Intent) {
        viewModelScope.launch {
            handleAuthResponseIntentUseCase(
                intent,
                { authSuccessEventChannel.trySendBlocking(Unit) },
                { toastEventChannel.trySendBlocking(R.string.auth_canceled)  }
            )
        }
    }
}