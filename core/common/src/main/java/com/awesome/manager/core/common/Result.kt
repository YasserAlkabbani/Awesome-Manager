package com.awesome.manager.core.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext

sealed interface AmResult<out T> {
    data class Success<T>(val data: T, val freshData: Boolean) : AmResult<T>
    data class Error(val throwable: Throwable) : AmResult<Nothing>
    data class Loading(val progress: Int = 0) : AmResult<Nothing>
}


fun <T> Flow<T>.asAmResult(): Flow<AmResult<T>> =
    map<T, AmResult<T>> { AmResult.Success(data = it,freshData = true) }
        .onStart { emit(AmResult.Loading()) }
        .catch { throwable -> emit(AmResult.Error(throwable)) }
        .flowOn(Dispatchers.Default)

suspend inline fun <T> amRequest(crossinline requestData: suspend () -> T?) = flow<AmResult<T>> {
    val data: T = requestData()!!
    emit(AmResult.Success(data=data,freshData = true))
}
    .onStart { emit(AmResult.Loading()) }
    .catch { throwable -> emit(AmResult.Error(throwable)) }
    .flowOn(Dispatchers.Default)

suspend inline fun amInsert(crossinline insertTask: suspend () -> Unit) {
    withContext(Dispatchers.IO) { insertTask() }
}

fun <T> amRequest(
    requestCacheData: suspend () -> T?,
    requestFreshData: suspend () -> T?,
    refreshCacheData: suspend (T) -> Unit,
    forceUpdate: Boolean
) = flow<AmResult<T>> {

    val cacheData = requestCacheData()

    if (cacheData != null && !forceUpdate) {
        emit(AmResult.Success(data = cacheData, freshData = false))
    } else {
        val freshData: T = requestFreshData()!!
        refreshCacheData(freshData)
        val freshCacheData: T = requestCacheData()!!
        emit(AmResult.Success(data = freshCacheData,freshData = true))
    }
}
    .onStart { emit(AmResult.Loading()) }
    .catch { throwable -> emit(AmResult.Error(throwable)) }
    .flowOn(Dispatchers.Default)

