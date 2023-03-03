package com.neophron.account.domain.usecases

import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.SignInResult

class SignInUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(signInData: SignInData): SignInResult =
        accountRepository.signIn(signInData)
}