package com.petproject.workflow.presentation.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Employee
import com.petproject.workflow.domain.usecases.GetCurrentEmployeeUseCase
import com.petproject.workflow.domain.usecases.GetLogoutPageIntentUseCase
import com.petproject.workflow.domain.usecases.LoadCurrentEmployeePhoto
import com.petproject.workflow.domain.usecases.SignOutUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getLogoutPageIntentUseCase: GetLogoutPageIntentUseCase,
    private val getCurrentEmployeeUseCase: GetCurrentEmployeeUseCase,
    private val loadCurrentEmployeePhoto: LoadCurrentEmployeePhoto,
    val requestManager: RequestManager
) : ViewModel() {

    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()

    private val _employee = MutableLiveData<Employee>()
    public val employee: LiveData<Employee> get() = _employee

    private val _navigateToLoginScreen = MutableLiveData(false)
    val navigateToLoginScreen: LiveData<Boolean> get() = _navigateToLoginScreen

    init {
        viewModelScope.launch {
            _employee.value = getCurrentEmployeeUseCase.invoke()
        }
    }

    fun loadPhoto(callback: (String) -> Unit) {
        viewModelScope.launch {
            loadCurrentEmployeePhoto(callback);
        }
    }

    fun logout() {
        val logoutPageIntent = getLogoutPageIntentUseCase()
        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
    }

    fun webLogoutComplete() {
        viewModelScope.launch {
            signOutUseCase()
        }
        logoutCompletedEventChannel.trySendBlocking(Unit)
        _navigateToLoginScreen.value = true
    }
}