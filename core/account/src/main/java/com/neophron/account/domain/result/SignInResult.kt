package com.neophron.account.domain.result

sealed class SignInResult {

    object Success : SignInResult()

    sealed class Error : SignInResult() {
        object EmptyFirstName : Error()
        object EmptyLastName : Error()
        object EmptyEmail : Error()
        class NoValidEmail(val correctExample: String?) : Error()
        object EmptyPassword : Error()
        object TooShortPassword : Error()
        class Unknown(e: Exception) : Error()
    }

}