package com.neophron.network.account.source_impl

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.neophron.database.account.AccountDao
import com.neophron.database.account.models.AccountEntity
import com.neophron.database.account.models.AvatarTuple
import com.neophron.mylibrary.map
import com.neophron.network.UserTokenStore
import com.neophron.network.account.source.AccountNetworkDataSource
import com.neophron.network.account.source.models.AccountResponse
import com.neophron.network.account.source.models.ChangeAvatarBody
import com.neophron.network.account.source.models.LogInBody
import com.neophron.network.account.source.models.SignInBody
import com.neophron.network.base.ClientSideException
import kotlinx.coroutines.delay

class FakeNetworkAccountDataSource(
    private val accountDao: AccountDao,
    private val userTokenStore: UserTokenStore
) : AccountNetworkDataSource {

    override suspend fun getUserAccount(): AccountResponse {
        delay(1000)
        val userId = userTokenStore.token ?: "-1"
        val accountEntity = accountDao.getUserAccount(userId.toLong())
        return accountEntity?.map { AccountResponse(firstName, lastName, email, avatarUrl) }
            ?: throw ClientSideException(code = 404)
    }

    override suspend fun signIn(signInBody: SignInBody): AccountResponse =
        try {
            delay(2000)
            val accountEntity = signInBody.map {
                AccountEntity(0, firstName, lastName, email, password, null)
            }
            val userId = accountDao.signIn(accountEntity)
            userTokenStore.setToken(userId.toString())
            accountEntity.map { AccountResponse(firstName, lastName, email, avatarUrl) }

        } catch (e: SQLiteConstraintException) {
            throw ClientSideException(409)
        }

    override suspend fun logIn(logInBody: LogInBody): AccountResponse {
        delay(2000)
        val accountEntity = accountDao.logIn(logInBody.email) ?: throw ClientSideException(404)
        if (accountEntity.password != logInBody.password) throw ClientSideException(409)
        userTokenStore.setToken(accountEntity.id.toString())
        return accountEntity.map { AccountResponse(firstName, lastName, email, avatarUrl) }
    }

    override suspend fun changeAvatar(changeAvatarBody: ChangeAvatarBody) {
        try {
            delay(2000)
            val userId = userTokenStore.token ?: "-1"
            val avatarUrl = changeAvatarBody.file?.toURI().toString()
            accountDao.changeAvatar(AvatarTuple(userId.toLong(), avatarUrl))
        } catch (e: SQLiteConstraintException) {
            throw ClientSideException(404)
        }
    }
}
















