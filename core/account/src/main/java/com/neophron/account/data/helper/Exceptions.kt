package com.neophron.account.data.helper

import com.neophron.account.domain.result.BaseErrorType
import com.neophron.account.domain.result.ErrorType
import com.neophron.network.base.BackendSideException
import com.neophron.network.base.ClientSideException
import com.neophron.network.base.ConnectionException
import com.neophron.network.base.Http

inline fun <T> wrapNetworkRequest(
    request: () -> T,
    onError: (e: Exception) -> T
) = try {
    request()
} catch (e: Exception) {
    onError(e)
}


inline fun Exception.toErrorType(
    on404NotFound: () -> ErrorType = { BaseErrorType.Unknown(this) },
    on409Conflict: () -> ErrorType = { BaseErrorType.Unknown(this) },
    onBackendFailed: () -> ErrorType = { BaseErrorType.BackendFailed },
    onConnectionFailed: () -> ErrorType = { BaseErrorType.NoConnection },
): ErrorType = when (this) {
    is ClientSideException -> {
        when (this.code) {
            Http.NOT_FOUND -> on404NotFound()
            Http.CONFLICT -> on409Conflict()
            else -> BaseErrorType.Unknown(this)
        }
    }
    is BackendSideException -> onBackendFailed()
    is ConnectionException -> onConnectionFailed()
    else -> BaseErrorType.Unknown(this)
}

