package com.neophron.database.account.models

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class AvatarTuple(
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?
)