package com.neophron.account.domain.models

data class SignInData(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)