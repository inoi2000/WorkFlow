package com.petproject.workflow.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.usecases.CreateStatementUseCase
import com.petproject.workflow.domain.usecases.GetStatementByIdUseCase
import javax.inject.Inject

class CreateStatementViewModel @Inject constructor(
    val statement: Statement,
    private val createStatementUseCase: CreateStatementUseCase,
    private val getEmployeeByIdUseCase: GetStatementByIdUseCase,
    private val getCarByIdUseCase: GetStatementByIdUseCase,
    private val getTrailerByIdUseCase: GetStatementByIdUseCase
): ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _statementCreationResult = MutableLiveData<Boolean>()
    val statementCreationResult: LiveData<Boolean> get() = _statementCreationResult


}