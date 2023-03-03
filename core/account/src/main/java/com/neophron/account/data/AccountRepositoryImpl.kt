package com.neophron.account.data

import com.neophron.account.domain.models.ChangeAvatarData
import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.*
import kotlinx.coroutines.flow.Flow

internal class AccountRepositoryImpl(

) : AccountRepository {

    override fun getAccount(): Flow<AccountResult> {
        TODO("Not yet implemented")
    }

    override suspend fun signIn(signInData: SignInData): SignInResult {
        TODO("Not yet implemented")
    }

    override suspend fun logIn(logInData: LogInData): LogInResult {
        TODO("Not yet implemented")
    }

    override suspend fun changeAvatar(changeAvatarData: ChangeAvatarData): ChangeAvatarResult {
        TODO("Not yet implemented")
    }

    override suspend fun logOut(): LogOutResult {
        TODO("Not yet implemented")
    }
}