package com.neophron.home.domain.result

sealed class ErrorType

sealed class BaseErrorType : ErrorType() {
    object NoConnection : BaseErrorType()
    object BackendFailed : BaseErrorType()
    class Unknown(val e: Exception? = null) : BaseErrorType()
}