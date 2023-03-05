package com.neophron.profile.presentation

import androidx.annotation.StringRes

sealed class ProfileUiEvent {
    class ToastMessage(@StringRes val messageRes: Int) : ProfileUiEvent()
}

