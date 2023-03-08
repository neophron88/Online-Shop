package com.neophron.home.data.helper

import com.neophron.home.domain.result.BaseErrorType
import com.neophron.network.base.BackendSideException
import com.neophron.network.base.ConnectionException

inline fun Exception.toErrorType(
    onBackendFailed: () -> BaseErrorType = { BaseErrorType.BackendFailed },
    onConnectionFailed: () -> BaseErrorType = { BaseErrorType.NoConnection },
): BaseErrorType = when (this) {
    is BackendSideException -> onBackendFailed()
    is ConnectionException -> onConnectionFailed()
    else -> BaseErrorType.Unknown(this)
}
