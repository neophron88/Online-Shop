package com.neophron.feature.exceptions

import com.neophron.feature.result.ErrorType
import com.neophron.feature.result.SingleResult

inline fun <T> wrapNetworkExceptions(run: () -> T): SingleResult<T> =
    try {
        SingleResult.Success(run())
    } catch (e: Exception) {
        SingleResult.Error(ErrorType.NoConnection)
    } catch (e: Exception) {
        SingleResult.Error(ErrorType.BackendFailed)
    } catch (e: Exception) {
        SingleResult.Error(ErrorType.Unknown)
    } catch (e: Exception) {
        SingleResult.Error(ErrorType.Unknown)
    }

