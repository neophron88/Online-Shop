package com.neophron.mylibrary.offline_first

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow


inline fun <LOCAL, NETWORK, RESULT> offlineFirst(
    longLiveScope: CoroutineScope,
    crossinline localDataFlow: () -> Flow<LOCAL?>,
    crossinline syncWithNetwork: suspend () -> NETWORK?,
    crossinline updateLocalData: suspend (NETWORK) -> Unit,
    crossinline pending: suspend () -> RESULT,
    crossinline syncedError: suspend (e: Exception) -> RESULT,
    crossinline result: suspend (LOCAL?) -> RESULT,
): Flow<RESULT> = flow {

    val localData = localDataFlow()
    emit(result(localData.first()))

    emit(pending())

    val syncResult = longLiveScope.async {
        try {
            val networkData = syncWithNetwork()
            if (networkData != null)
                updateLocalData(networkData)
            SyncResult.SyncSuccess
        } catch (e: Exception) {
            SyncResult.SyncFault(e)
        }
    }.await()

    if (syncResult is SyncResult.SyncFault)
        emit(syncedError(syncResult.e))

    localData.collect {
        emit(result(it))
    }
}

sealed class SyncResult {
    object SyncSuccess : SyncResult()
    class SyncFault(val e: Exception) : SyncResult()
}

