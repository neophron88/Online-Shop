package com.neophron.feature.result

import android.database.SQLException

sealed class ErrorType {
    class DatabaseError(e: SQLException) : ErrorType()
    object NoConnection : ErrorType()
    object BackendFailed : ErrorType()
    object Unknown : ErrorType()
}


class TypeException(val errorType: ErrorType) : Throwable()

