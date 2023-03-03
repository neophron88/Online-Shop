package com.neophron.account.domain.result

sealed class ChangeAvatarResult {

    object Success : ChangeAvatarResult()

    sealed class Error : ChangeAvatarResult() {
        class Unknown(e: Exception) : Error()
    }
}

