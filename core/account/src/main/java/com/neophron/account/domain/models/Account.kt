package com.neophron.account.domain.models

data class Account(
    val firstName: String,
    val lastName: String,
    val email: String,
    val avatarUrl: String?
)
