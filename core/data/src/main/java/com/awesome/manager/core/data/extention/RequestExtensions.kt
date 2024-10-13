package com.awesome.manager.core.data.extention

import com.awesome.manager.core.common.AmUIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import timber.log.Timber


inline fun <T> Flow<T>.asAmResult(
    crossinline taskToDo: suspend (T) -> Unit,
    crossinline doOnSuccess: suspend () -> Unit,
): Flow<AmUIState<T>> =
    map<T, AmUIState<T>> {
        Timber.d("REFRESH_TRANSACTION TO_DO")
        taskToDo(it)
        Timber.d("REFRESH_TRANSACTION TASK_TO_DO")
        doOnSuccess()
        Timber.d("REFRESH_TRANSACTION DO_ON_SUCCESS")
        AmUIState.Success(data = it)
    }
        .onStart {
            Timber.d("REFRESH_TRANSACTION ON_START")
            emit(AmUIState.Loading())
        }
        .catch { throwable ->
            Timber.d("REFRESH_TRANSACTION ON_ERROR " + throwable.message)
            emit(throwable.asAmError())
        }
        .flowOn(Dispatchers.Default)

suspend inline fun <T> amRequest(crossinline requestData: suspend () -> T?) = flow<AmUIState<T>> {
    val data: T = requestData()!!
    emit(AmUIState.Success(data = data))
}
    .onStart { emit(AmUIState.Loading()) }
    .catch { throwable ->
        Timber.d("TEST_AUTH CATCH_ERROR ${throwable.message}")
        emit(throwable.asAmError())
    }
    .flowOn(Dispatchers.Default)

suspend inline fun amInsert(crossinline insertTask: suspend () -> Unit) {
    withContext(Dispatchers.IO) { insertTask() }
}

//fun <T> amRequest(
//    requestCacheData: suspend () -> T?,
//    requestFreshData: suspend () -> T?,
//    refreshCacheData: suspend (T) -> Unit,
//    forceUpdate: Boolean
//) = flow<AmResult<T>> {
//
//    val cacheData = requestCacheData()
//
//    if (cacheData != null && !forceUpdate) {
//        emit(AmResult.Success(data = cacheData))
//    } else {
//        val freshData: T = requestFreshData()!!
//        refreshCacheData(freshData)
//        val freshCacheData: T = requestCacheData()!!
//        emit(AmResult.Success(data = freshCacheData))
//    }
//}
//    .onStart { emit(AmResult.Loading()) }
//    .catch { throwable -> emit(throwable.asAmError()) }
//    .flowOn(Dispatchers.Default)
//
