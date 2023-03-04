package com.neophron.account.data.helper

import com.neophron.account.domain.models.LogInData
import com.neophron.account.domain.models.SignInData
import com.neophron.account.domain.result.LogInErrorType
import com.neophron.account.domain.result.LogInResult
import com.neophron.account.domain.result.SignInErrorType
import com.neophron.account.domain.result.SignInResult

fun SignInData.validate(): SignInResult {
    val errorType = if (firstName.isBlank()) SignInErrorType.EmptyFirstName
    else if (lastName.isBlank()) SignInErrorType.EmptyLastName
    else if (email.isBlank()) SignInErrorType.EmptyEmail
    else if (isNotValidEmail(email)) SignInErrorType.NoValidEmail
    else if (password.isBlank()) SignInErrorType.EmptyPassword
    else if (isTooShortPassword(password)) SignInErrorType.TooShortPassword
    else null
    return if (errorType == null) SignInResult.Success else SignInResult.Error(errorType)

}


fun LogInData.validate(): LogInResult {
    val errorType = if (email.isBlank()) LogInErrorType.EmptyEmail
    else if (isNotValidEmail(email)) LogInErrorType.NoValidEmail
    else if (password.isBlank()) LogInErrorType.EmptyPassword
    else if (isTooShortPassword(password)) LogInErrorType.TooShortPassword
    else null
    return if (errorType == null) LogInResult.Success else LogInResult.Error(errorType)
}

private fun isTooShortPassword(password: String) = password.length < 5

private fun isNotValidEmail(email: String) = !email.matches(
    Regex(
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
    )
)
