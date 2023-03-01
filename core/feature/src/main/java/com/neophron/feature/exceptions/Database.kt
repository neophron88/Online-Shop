package com.neophron.feature.exceptions

import android.database.sqlite.SQLiteException
import com.neophron.feature.result.ErrorType
import com.neophron.feature.result.SingleResult


inline fun <T> wrapDatabaseExceptions(run: () -> T): SingleResult<T> =
    try {
        SingleResult.Success(run())
    } catch (e: SQLiteException) {
        SingleResult.Error(ErrorType.DatabaseError(e))
    }
