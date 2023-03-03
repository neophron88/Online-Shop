package com.neophron.account.domain.usecases

import com.neophron.account.domain.models.ChangeAvatarData
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.ChangeAvatarResult

class ChangeAvatarUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(changeAvatarData: ChangeAvatarData): ChangeAvatarResult =
        accountRepository.changeAvatar(changeAvatarData)
}
