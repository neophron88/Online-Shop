package com.neophron.network.account.source

import com.neophron.network.account.source.models.*

interface AccountNetworkDataSource {

    suspend fun getUserAccount(): AccountResponse

    suspend fun signIn(signInBody: SignInBody): AccountResponse

    suspend fun logIn(logInBody: LogInBody): AccountResponse

    suspend fun changeAvatar(changeAvatarBody: ChangeAvatarBody):AvatarUrlResponse

}