package com.neophron.account.domain.result

sealed class SignInResult {
    object Success : SignInResult()
    class Error(val type: ErrorType) : SignInResult()
}

sealed class SignInErrorType:ErrorType() {
    object EmptyFirstName : SignInErrorType()
    object EmptyLastName : SignInErrorType()
    object EmptyEmail : SignInErrorType()
    object NoValidEmail : SignInErrorType()
    object EmptyPassword : SignInErrorType()
    object TooShortPassword : SignInErrorType()
    object AccountAlreadyExists : SignInErrorType()

}
