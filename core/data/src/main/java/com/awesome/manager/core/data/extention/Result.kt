package com.awesome.manager.core.data.extention

import android.util.Log
import com.awesome.manager.core.network.NetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.net.UnknownHostException


sealed interface AmResult<out T> {
    data class Success<T>(val data: T, val freshData: Boolean) : AmResult<T>
    data class Error(val networkError: NetworkError) : AmResult<Nothing>
    data class Loading(val progress: Int = 0) : AmResult<Nothing>

}

fun Throwable.asAmError(): AmResult.Error = AmResult.Error(
    when (this) {
        is NetworkError -> this
        is UnknownHostException -> NetworkError.ConnectionError
        else -> message?.let { NetworkError.OtherError(it) } ?: NetworkError.ConnectionError
    }
)


inline fun <T> Flow<T>.asAmResult(
    crossinline taskToDo: suspend (T) -> Unit,
    crossinline doOnSuccess: suspend () -> Unit,
): Flow<AmResult<T>> =
    map<T, AmResult<T>> {
        Log.d("com.awesome.manager", "REFRESH_TRANSACTION TO_DO")
        taskToDo(it)
        Log.d("com.awesome.manager", "REFRESH_TRANSACTION TASK_TO_DO")
        doOnSuccess()
        Log.d("com.awesome.manager", "REFRESH_TRANSACTION DO_ON_SUCCESS")
        AmResult.Success(data = it, freshData = true)
    }
        .onStart {
            Log.d("com.awesome.manager", "REFRESH_TRANSACTION ON_START")
            emit(AmResult.Loading())
        }
        .catch { throwable ->
            Log.d("com.awesome.manager", "REFRESH_TRANSACTION ON_ERROR ${throwable.message}")
            emit(throwable.asAmError())
        }
        .flowOn(Dispatchers.Default)

suspend inline fun <T> amRequest(crossinline requestData: suspend () -> T?) = flow<AmResult<T>> {
    val data: T = requestData()!!
    emit(AmResult.Success(data = data, freshData = true))
}
    .onStart { emit(AmResult.Loading()) }
    .catch { throwable ->
        Timber.d("TEST_AUTH CATCH_ERROR ${throwable.message}")
        emit(throwable.asAmError())
    }
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
        emit(AmResult.Success(data = freshCacheData, freshData = true))
    }
}
    .onStart { emit(AmResult.Loading()) }
    .catch { throwable -> emit(throwable.asAmError()) }
    .flowOn(Dispatchers.Default)

