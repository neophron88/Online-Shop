package com.neophron.account.data.preference

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.neophron.account.data.preference.models.AccountPrefData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@SuppressLint("ApplySharedPref")
class AccountPreferenceImpl(
    appContext: Application
) : AccountPreference {

    private val preferences = appContext.getSharedPreferences("account", Context.MODE_PRIVATE)
    private val dispatcher = Dispatchers.IO
    private val preferenceFlow = MutableSharedFlow<Unit>(
        1,
        0,
        BufferOverflow.DROP_OLDEST
    ).also { it.tryEmit(Unit) }


    override fun getAccountPrefData(): Flow<AccountPrefData?> =
        preferenceFlow
            .map { fetchData() }
            .flowOn(dispatcher)


    private fun fetchData(): AccountPrefData? {
        val firstName = preferences.getString("firstName", null) ?: return null
        val lastName = preferences.getString("lastName", null) ?: return null
        val email = preferences.getString("email", null) ?: return null
        val avatarUrl = preferences.getString("avatarUrl", null)
        return AccountPrefData(firstName, lastName, email, avatarUrl)

    }

    override suspend fun saveAccountPrefData(account: AccountPrefData) =
        withContext(dispatcher) {
            preferences.edit().apply {
                putString("firstName", account.firstName)
                putString("lastName", account.lastName)
                putString("email", account.email)
                putString("avatarUrl", account.avatarUrl)
            }.commit()
            preferenceFlow.emit(Unit)
        }


    override suspend fun clearAccount() =
        withContext(dispatcher) {
            preferences.edit().apply { clear() }.commit()
            preferenceFlow.emit(Unit)
        }

    override suspend fun setAvatarUrl(avatarUrl: String?) = withContext(dispatcher) {
        preferences.edit().apply {
            putString("avatarUrl", avatarUrl)
        }.commit()
        preferenceFlow.emit(Unit)
    }

}


