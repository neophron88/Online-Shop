package com.neophron.account.data.preference

import com.neophron.account.data.preference.models.AccountPrefData
import kotlinx.coroutines.flow.Flow

interface AccountPreference {

    fun getAccountPrefData(): Flow<AccountPrefData?>

    suspend fun saveAccountPrefData(account: AccountPrefData)

    suspend fun setAvatarUrl(avatarUrl: String?)

    suspend fun clearAccount()

}

