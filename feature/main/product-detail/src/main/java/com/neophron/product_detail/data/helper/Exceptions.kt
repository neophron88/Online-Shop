package com.neophron.product_detail.data.helper

import com.neophron.network.base.BackendSideException
import com.neophron.network.base.ConnectionException
import com.neophron.product_detail.domain.result.BaseErrorType

inline fun Exception.toErrorType(
    onBackendFailed: () -> BaseErrorType = { BaseErrorType.BackendFailed },
    onConnectionFailed: () -> BaseErrorType = { BaseErrorType.NoConnection },
): BaseErrorType = when (this) {
    is BackendSideException -> onBackendFailed()
    is ConnectionException -> onConnectionFailed()
    else -> BaseErrorType.Unknown(this)
}
