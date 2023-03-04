package com.neophron.account.domain.result

sealed class ChangeAvatarResult {
    object Success : ChangeAvatarResult()
    class Error(val type: ErrorType) : ChangeAvatarResult()
}


