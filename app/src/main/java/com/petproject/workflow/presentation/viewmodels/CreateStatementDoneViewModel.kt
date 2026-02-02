package com.petproject.workflow.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petproject.workflow.domain.entities.Statement
import com.petproject.workflow.domain.usecases.CreateStatementUseCase
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

class CreateStatementDoneViewModel @Inject constructor(
    private val createStatementUseCase: CreateStatementUseCase
) : ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    val loadingState: LiveData<Boolean> get() = _loadingState

    private val _statementCreationResult = MutableLiveData<Result<Statement>>()
    val statementCreationResult: LiveData<Result<Statement>> get() = _statementCreationResult

    private val _navigateToJourneys = MutableLiveData<Boolean>()
    val navigateToJourneys: LiveData<Boolean> get() = _navigateToJourneys

    private val _createdStatement = MutableLiveData<Statement?>()
    val createdStatement: LiveData<Statement?> get() = _createdStatement

    // Данные для отображения в UI
    val logistName = ObservableField<String>()
    val destinationAddress = ObservableField<String>()
    val destinationTime = ObservableField<String>()
    val contactPhone = ObservableField<String>()

    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm", Locale.getDefault())

    fun setStatementData(statement: Statement) {
        updateDisplayData(statement)
    }

    private fun updateDisplayData(statement: Statement) {
        logistName.set(statement.logist.name)
        destinationAddress.set(statement.destinationAddress)
        destinationTime.set(statement.destinationTime.format(dateTimeFormatter))
        contactPhone.set(statement.contactPhone)
    }

    suspend fun createStatement(statement: Statement): Statement {
        _loadingState.value = true

        return try {
            val createdStatement = createStatementUseCase(statement)

            // Сохраняем созданную заявку
            _createdStatement.value = createdStatement

            // Обновляем данные для отображения
            updateDisplayData(createdStatement)

            // Отправляем результат успешного создания
            _statementCreationResult.value = Result.success(createdStatement)

            // Завершаем загрузку
            _loadingState.value = false

            createdStatement
        } catch (e: Exception) {
            // Отправляем результат с ошибкой
            _statementCreationResult.value = Result.failure(e)

            // Завершаем загрузку
            _loadingState.value = false

            throw e
        }
    }

    fun onCreateAnotherStatement() {
        // Навигация будет обработана во Fragment
    }

    fun onGoToJourneys() {
        _navigateToJourneys.value = true
    }

    fun onNavigationComplete() {
        _navigateToJourneys.value = false
    }
}