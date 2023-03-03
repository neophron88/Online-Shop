package com.neophron.account.domain.repositories

import com.neophron.account.domain.models.ChangeAvatarData
import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.result.*
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    fun getAccount(): Flow<AccountResult>

    suspend fun signIn(signInData: SignInData): SignInResult

    suspend fun logIn(logInData: LogInData): LogInResult

    suspend fun changeAvatar(changeAvatarData: ChangeAvatarData): ChangeAvatarResult

    suspend fun logOut(): LogOutResult
}