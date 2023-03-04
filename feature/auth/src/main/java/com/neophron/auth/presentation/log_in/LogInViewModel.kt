package com.neophron.auth.presentation.log_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.result.BaseErrorType
import com.neophron.account.domain.result.ErrorType
import com.neophron.account.domain.result.LogInErrorType
import com.neophron.account.domain.result.LogInResult
import com.neophron.account.domain.usecases.LogInUseCase
import com.neophron.auth.presentation.helper.toStringRes
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import com.neophron.mylibrary.throwNotHandled
import com.neophron.ui.R
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class LogInViewModel @AssistedInject constructor(
    private val logInUseCase: LogInUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<LogInUiState> = MutableLiveData()
    val uiState: LiveData<LogInUiState> get() = _uiState

    private val _uiEvent: MutableSingleUseData<LogInUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<LogInUiEvent> get() = _uiEvent


    fun logIn(email: String, password: String) = viewModelScope.launch {
        _uiState.value = LogInUiState(logInInProgress = true)
        val logInResult = logInUseCase(LogInData(email, password))

        if (logInResult is LogInResult.Success)
            processSuccess()

        if (logInResult is LogInResult.Error)
            processError(logInResult.type)
    }

    private fun processSuccess() {
        _uiEvent.value = LogInUiEvent.LogInSuccess
        _uiState.value = LogInUiState()
    }

    private fun processError(errorType: ErrorType) = when (errorType) {
        is BaseErrorType -> {
            _uiEvent.value = LogInUiEvent.ToastMessage(errorType.toStringRes())
            _uiState.value = LogInUiState()
        }
        is LogInErrorType.EmptyEmail ->
            _uiState.value = LogInUiState(emailErrorRes = R.string.empty_field)
        is LogInErrorType.NoValidEmail ->
            _uiState.value = LogInUiState(emailErrorRes = R.string.invalid_email)
        is LogInErrorType.EmptyPassword ->
            _uiState.value = LogInUiState(passwordErrorRes = R.string.empty_field)
        is LogInErrorType.TooShortPassword ->
            _uiState.value = LogInUiState(passwordErrorRes = R.string.too_short_password)
        is LogInErrorType.NoSuchAccount ->
            _uiState.value = LogInUiState(emailErrorRes = R.string.no_such_account)
        is LogInErrorType.PasswordMisMatch ->
            _uiState.value = LogInUiState(passwordErrorRes = R.string.incorrect_password)
        else -> errorType.throwNotHandled()
    }


}