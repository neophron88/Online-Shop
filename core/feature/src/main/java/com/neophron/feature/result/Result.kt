package com.neophron.feature.result


sealed class SingleResult<T> {
    class Success<T>(val value: T) : SingleResult<T>()
    class Error<T>(val type: ErrorType) : SingleResult<T>()
}

inline fun <T, R> SingleResult<T>.map(block: (T) -> R): SingleResult<R> =
    if (this is SingleResult.Success) SingleResult.Success(block(this.value))
    else this as SingleResult<R>

