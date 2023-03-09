package com.neophron.product_detail.presentation.helper

import androidx.annotation.StringRes
import com.neophron.product_detail.domain.result.BaseErrorType

@StringRes
fun BaseErrorType.toStringRes() = when (this) {
    is BaseErrorType.BackendFailed -> com.neophron.ui.R.string.server_failed
    is BaseErrorType.NoConnection -> com.neophron.ui.R.string.no_connection
    is BaseErrorType.Unknown -> com.neophron.ui.R.string.something_went_wrong
}