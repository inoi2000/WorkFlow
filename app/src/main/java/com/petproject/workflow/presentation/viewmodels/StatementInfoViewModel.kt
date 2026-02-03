package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.RequestManager
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.usecases.GetStatementByIdUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class StatementInfoViewModel @Inject constructor(
    private val statementId: String,
    private val getStatementByIdUseCase: GetStatementByIdUseCase,
    val requestManager: RequestManager
) : ViewModel() {

    private val _statement = MutableLiveData<Statement>()
    val statement: LiveData<Statement> get() = _statement

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _statement.value = getStatementByIdUseCase(statementId)
        }
    }
}