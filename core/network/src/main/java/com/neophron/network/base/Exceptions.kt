package com.neophron.network.base

import com.neophron.network.base.Http.BACKEND
import com.neophron.network.base.Http.CLIENT
import retrofit2.HttpException
import java.io.IOException

abstract class NetworkException(
    cause: Exception?,
    code: Int?,
) : Exception("Network error code $code", cause)

class ConnectionException(cause: Exception? = null) : NetworkException(cause,null)

class BackendSideException(val code: Int, cause: Exception? = null) : NetworkException(cause, code)

class ClientSideException(val code: Int, cause: Exception? = null) : NetworkException(cause, code)

class UnknownNetworkException(val code: Int, cause: Exception? = null) : NetworkException(cause, code)


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

internal fun parseHttpCodeToException(code: Int, cause: Exception): NetworkException =
    if (code >= BACKEND)
        BackendSideException(code, cause)
    else if (code in CLIENT until BACKEND)
        ClientSideException(code, cause)
    else
        UnknownNetworkException(code, cause)
