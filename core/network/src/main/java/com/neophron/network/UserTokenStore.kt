package com.neophron.network

import android.app.Application
import android.content.Context
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext

interface UserTokenStore {

    var token: String?

    suspend fun setToken(token: String?)

}

internal class UserTokenStoreImpl(
    application: Application
) : UserTokenStore {

    private val preference = application.getSharedPreferences("userToken", Context.MODE_PRIVATE)

    private val mutex = Mutex()

    override var token: String? = preference.getString("token", null)

    override suspend fun setToken(token: String?) {
        withContext(Dispatchers.IO) {
            preference.edit(commit = true) { putString("token", token) }
            mutex.withLock { this@UserTokenStoreImpl.token = token }
        }
    }

}