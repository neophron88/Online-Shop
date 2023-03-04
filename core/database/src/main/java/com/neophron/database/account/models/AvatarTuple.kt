package com.neophron.database.account.models

import androidx.room.ColumnInfo

class AvatarTuple(
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String?
)