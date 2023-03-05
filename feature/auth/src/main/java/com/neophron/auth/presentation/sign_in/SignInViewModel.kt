package com.neophron.auth.presentation.sign_in

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.result.BaseErrorType
import com.neophron.account.domain.result.ErrorType
import com.neophron.account.domain.result.SignInErrorType
import com.neophron.account.domain.result.SignInResult
import com.neophron.account.domain.usecases.SignInUseCase
import com.neophron.account.presentation.toStringRes
import com.neophron.mylibrary.single_use_data.MutableSingleUseData
import com.neophron.mylibrary.single_use_data.SingleUseData
import com.neophron.mylibrary.throwNotHandled
import com.neophron.ui.R
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignInViewModel @AssistedInject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _uiState: MutableLiveData<SignInUiState> = MutableLiveData()
    val uiState: LiveData<SignInUiState> get() = _uiState

    private val _uiEvent: MutableSingleUseData<SignInUiEvent> = MutableSingleUseData()
    val uiEvent: SingleUseData<SignInUiEvent> get() = _uiEvent

    fun signIn(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _uiState.value = SignInUiState(signInInProgress = true)
        val signInResult = signInUseCase(SignInData(firstName, lastName, email, password))

        if (signInResult is SignInResult.Success)
            processSuccess()

        if (signInResult is SignInResult.Error)
            processError(signInResult.type)
    }

    private fun processSuccess() {
        _uiEvent.value = SignInUiEvent.SignInSuccess
        _uiState.value = SignInUiState()
    }

    private fun processError(errorType: ErrorType) = when (errorType) {
        is BaseErrorType -> {
            _uiEvent.value = SignInUiEvent.ToastMessage(errorType.toStringRes())
            _uiState.value = SignInUiState()
        }
        is SignInErrorType.EmptyEmail ->
            _uiState.value = SignInUiState(emailErrorRes = R.string.empty_field)
        is SignInErrorType.NoValidEmail ->
            _uiState.value = SignInUiState(emailErrorRes = R.string.invalid_email)
        is SignInErrorType.AccountAlreadyExists ->
            _uiState.value = SignInUiState(emailErrorRes = R.string.account_already_exists)
        is SignInErrorType.EmptyPassword ->
            _uiState.value = SignInUiState(passwordErrorRes = R.string.empty_field)
        is SignInErrorType.TooShortPassword ->
            _uiState.value = SignInUiState(passwordErrorRes = R.string.too_short_password)
        is SignInErrorType.EmptyFirstName ->
            _uiState.value = SignInUiState(firstNameErrorRes = R.string.empty_field)
        is SignInErrorType.EmptyLastName ->
            _uiState.value = SignInUiState(lastNameErrorRes = R.string.empty_field)
        else -> errorType.throwNotHandled()
    }

}
