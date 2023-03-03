package com.neophron.account.domain.usecases

import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.LogOutResult

class LogOutUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): LogOutResult =
        accountRepository.logOut()
}