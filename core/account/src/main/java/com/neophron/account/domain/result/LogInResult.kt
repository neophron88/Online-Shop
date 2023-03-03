package com.neophron.account.domain.result

sealed class LogInResult {

    object Success : LogInResult()

    sealed class Error : LogInResult() {
        object EmptyEmail : Error()
        class NoValidEmail(val correctExample: String) : Error()
        object EmptyPassword : Error()
        object TooShortPassword : Error()
        class Unknown(e: Exception) : Error()
    }
}