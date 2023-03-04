package com.neophron.account.data.helper

import com.neophron.account.data.preference.models.AccountPrefData
import com.neophron.account.domain.models.Account
import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.models.SignInData
import com.neophron.network.account.source.models.AccountResponse
import com.neophron.network.account.source.models.LogInBody
import com.neophron.network.account.source.models.SignInBody


fun AccountResponse.mapToAccountPrefData() =
    AccountPrefData(firstName, lastName, email, avatarUrl)

fun AccountPrefData.mapToAccount() =
    Account(firstName, lastName, email, avatarUrl)

fun SignInData.mapToSignInBody() =
    SignInBody(firstName, lastName, email, password)

fun LogInData.mapToLogInBody() =
    LogInBody(email, password)
