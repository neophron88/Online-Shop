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
    crossinline onSyncError: suspend (e: Exception) -> Result<RESULT>,
    crossinline onPending: suspend () -> Result<RESULT>,
    crossinline onResult: suspend (LOCAL?) -> Result<RESULT>,
): Flow<Result<RESULT>> = flow {

    val localData = localDataFlow()
    emit(onResult(localData.first()))

    emit(onPending())

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
        emit(onSyncError(syncResult.e))

    localData.collect {
        emit(onResult(it))
    }
}

sealed class SyncResult {
    object SyncSuccess : SyncResult()
    class SyncFault(val e: Exception) : SyncResult()
}

