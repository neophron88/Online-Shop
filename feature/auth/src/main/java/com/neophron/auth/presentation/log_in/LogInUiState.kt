package com.neophron.auth.presentation.log_in

import androidx.annotation.StringRes

data class LogInUiState(
    @StringRes val emailErrorRes: Int? = null,
    @StringRes val passwordErrorRes: Int? = null,
    val logInInProgress: Boolean = false,
) {
    val logInNotInProgress: Boolean get() = !logInInProgress

}

