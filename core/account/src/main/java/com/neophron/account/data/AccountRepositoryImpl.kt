package com.neophron.account.data

import com.neophron.account.data.helper.*
import com.neophron.account.data.preference.AccountPreference
import com.neophron.account.domain.models.ChangeAvatarData
import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.result.*
import com.neophron.mylibrary.offline_first.offlineFirst
import com.neophron.network.UserTokenStore
import com.neophron.network.account.source.AccountNetworkDataSource
import com.neophron.network.account.source.models.ChangeAvatarBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

internal class AccountRepositoryImpl(
    private val networkSource: AccountNetworkDataSource,
    private val accountPreference: AccountPreference,
    private val userTokenStore: UserTokenStore,
    private val longLiveScope: CoroutineScope
) : AccountRepository {

    override fun getAccount(): Flow<AccountResult> = offlineFirst(
        longLiveScope = longLiveScope,
        localDataFlow = { accountPreference.getAccountPrefData() },
        syncWithNetwork = { networkSource.getUserAccount() },
        updateLocalData = { accountPreference.saveAccountPrefData(it.mapToAccountPrefData()) },
        pending = { AccountResult.Pending },
        syncError = {
            AccountResult.Error(
                it.toErrorType(on404NotFound = { AccountErrorType.NoAuthorized.also { logOut() } })
            )
        },
        result = {
            if (it == null) AccountResult.Error(AccountErrorType.NoAuthorized)
            else AccountResult.Success(it.mapToAccount())
        }
    )

    override suspend fun signIn(signInData: SignInData): SignInResult {
        val signInDataValidation = signInData.validate()
        if (signInDataValidation is SignInResult.Error) return signInDataValidation
        return wrapNetworkRequest(
            request = {
                val accountResponse = networkSource.signIn(signInData.mapToSignInBody())
                accountPreference.saveAccountPrefData(accountResponse.mapToAccountPrefData())
                SignInResult.Success
            },
            onError = {
                val type = it.toErrorType(on409Conflict = { SignInErrorType.AccountAlreadyExists })
                SignInResult.Error(type)
            }
        )
    }

    override suspend fun logIn(logInData: LogInData): LogInResult {
        val logInValidation = logInData.validate()
        if (logInValidation is LogInResult.Error) return logInValidation
        return wrapNetworkRequest(
            request = {
                val accountResponse = networkSource.logIn(logInData.mapToLogInBody())
                accountPreference.saveAccountPrefData(accountResponse.mapToAccountPrefData())
                LogInResult.Success
            },
            onError = {
                val type = it.toErrorType(
                    on404NotFound = { LogInErrorType.NoSuchAccount },
                    on409Conflict = { LogInErrorType.PasswordMisMatch }
                )
                LogInResult.Error(type)
            }
        )
    }

    override suspend fun changeAvatar(changeAvatarData: ChangeAvatarData): ChangeAvatarResult =
        wrapNetworkRequest(
            request = {
                val avatarUrlResponse =
                    networkSource.changeAvatar(ChangeAvatarBody(changeAvatarData.file))
                accountPreference.setAvatarUrl(avatarUrlResponse.avatarUrl)
                ChangeAvatarResult.Success
            },
            onError = { ChangeAvatarResult.Error(it.toErrorType()) }
        )

    override suspend fun logOut(): LogOutResult {
        accountPreference.clearAccount()
        userTokenStore.setToken(null)
        return LogOutResult.Success
    }
}