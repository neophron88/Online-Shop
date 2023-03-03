package com.neophron.account.domain.result

sealed class LogOutResult {

    object Success : LogOutResult()

    sealed class Error : LogOutResult() {
        class Unknown(e: Exception) : Error()
    }

}