package com.neophron.auth.presentation.sign_in

import androidx.annotation.StringRes

data class SignInUiState(
    @StringRes val firstNameErrorRes: Int? = null,
    @StringRes val lastNameErrorRes: Int? = null,
    @StringRes val emailErrorRes: Int? = null,
    @StringRes val passwordErrorRes: Int? = null,
    val signInInProgress: Boolean = false,
) {
    val signInNotInProgress: Boolean get() = !signInInProgress

}

