package com.neophron.account.domain.result

import com.neophron.account.domain.models.Account


sealed class AccountResult {
    class Success(val account: Account) : AccountResult()
    object Pending : AccountResult()
    class Error(val type: ErrorType) : AccountResult()
}

sealed class AccountErrorType : ErrorType() {
    object NoAuthorized : AccountErrorType()
}

