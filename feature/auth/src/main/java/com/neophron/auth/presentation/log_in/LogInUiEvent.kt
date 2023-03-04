package com.neophron.auth.presentation.log_in

import androidx.annotation.StringRes

sealed class LogInUiEvent {
    object LogInSuccess : LogInUiEvent()
    class ToastMessage(@StringRes val messageRes: Int) : LogInUiEvent()
}

