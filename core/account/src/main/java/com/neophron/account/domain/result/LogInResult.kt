package com.neophron.account.domain.result

sealed class LogInResult {
    object Success : LogInResult()
    class Error(val type: ErrorType) : LogInResult()
}

sealed class LogInErrorType:ErrorType() {
    object EmptyEmail : ErrorType()
    object NoValidEmail : ErrorType()
    object EmptyPassword : ErrorType()
    object TooShortPassword : ErrorType()
    object NoSuchAccount : ErrorType()
    object PasswordMisMatch : ErrorType()
}