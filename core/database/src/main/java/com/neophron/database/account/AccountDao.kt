package com.neophron.database.account

import androidx.room.*
import com.neophron.database.account.models.AccountEntity
import com.neophron.database.account.models.AvatarTuple

@Dao
interface AccountDao {

    @Query("SELECT * FROM account_table WHERE id=:userId")
    suspend fun getUserAccount(userId: Long): AccountEntity?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun signIn(accountEntity: AccountEntity): AccountEntity

    @Query("SELECT * FROM account_table WHERE email=:email")
    suspend fun logIn(email: String): AccountEntity?

    @Update
    suspend fun changeAvatar(avatarTuple: AvatarTuple)
}