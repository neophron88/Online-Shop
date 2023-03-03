package com.neophron.account.domain.result

import com.neophron.account.domain.models.Account


sealed class AccountResult {

    class Success(val account: Account) : AccountResult()

    object Pending : AccountResult()

    sealed class Error : AccountResult() {
        object NoAuthorized : Error()
        class Unknown(e: Exception) : Error()
    }
}

