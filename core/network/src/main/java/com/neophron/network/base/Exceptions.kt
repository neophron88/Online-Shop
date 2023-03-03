package com.neophron.network.base

import retrofit2.HttpException
import java.io.IOException

abstract class NetworkException(
    cause: Throwable?,
    code: Int?,
) : Throwable("Network error code $code", cause)

class ConnectionException(cause: Throwable? = null) : NetworkException(cause,null)

class BackendSideException(val code: Int, cause: Throwable? = null) : NetworkException(cause, code)

class ClientSideException(val code: Int, cause: Throwable? = null) : NetworkException(cause, code)

class UnknownNetworkException(val code: Int, cause: Throwable? = null) : NetworkException(cause, code)


internal inline fun <T> wrapRetrofitExceptions(
    block: () -> T,
): T {
    try {
        return block()
    } catch (e: HttpException) {
        throw parseHttpCodeToException(e.code(), e)
    } catch (e: IOException) {
        throw ConnectionException(e)
    }

}

internal fun parseHttpCodeToException(code: Int, cause: Throwable): NetworkException =
    if (code >= BACKEND)
        BackendSideException(code, cause)
    else if (code in CLIENT until BACKEND)
        ClientSideException(code, cause)
    else
        UnknownNetworkException(code, cause)
