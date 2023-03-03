package com.neophron.account.domain.usecases

import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.AccountResult
import kotlinx.coroutines.flow.Flow

class GetAccountUseCase(
    private val accountRepository: AccountRepository
) {
    operator fun invoke(): Flow<AccountResult> =
        accountRepository.getAccount()
}