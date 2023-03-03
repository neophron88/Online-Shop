package com.neophron.account.domain.usecases

import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.LogInResult

class LogInUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(logInData: LogInData): LogInResult =
        accountRepository.logIn(logInData)
}