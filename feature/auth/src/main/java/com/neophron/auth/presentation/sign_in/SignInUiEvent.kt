package com.neophron.auth.presentation.sign_in

import androidx.annotation.StringRes

sealed class SignInUiEvent {
    object SignInSuccess : SignInUiEvent()
    class ToastMessage(@StringRes val messageRes: Int) : SignInUiEvent()
}

