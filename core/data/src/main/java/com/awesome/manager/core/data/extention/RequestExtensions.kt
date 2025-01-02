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


inline fun <T, R> Flow<T>.asUIState(
    crossinline taskToDo: suspend (T) -> R
): Flow<AmUIState<R>> = map<T, AmUIState<R>> {
    Timber.d("TEST_AM FLOW_TO_UI_STATE BEFORE_TASK")
    val taskResult = taskToDo(it)
    Timber.d("TEST_AM FLOW_TO_UI_STATE AFTER_TASK")
    AmUIState.Success(data = taskResult)
}
    .onStart {
        Timber.d("TEST_AM FLOW_TO_UI_STATE START")
        emit(AmUIState.Loading())
    }
    .catch { throwable ->
        Timber.d("TEST_AM FLOW_TO_UI_STATE ERROR $throwable")
        emit(throwable.asAmError())
    }
    .flowOn(Dispatchers.Default)

inline fun <T> requestUIState(
    crossinline requestData: suspend () -> T
): Flow<AmUIState<T>> = flow<AmUIState<T>> {
    Timber.d("TEST_AM REQUEST_UI_STATE BEFORE_TASK")
    emit(AmUIState.Success(data = requestData()))
    Timber.d("TEST_AM REQUEST_UI_STATE AFTER_TASK")
}
    .onStart {
        Timber.d("TEST_AM REQUEST_UI_STATE START")
        emit(AmUIState.Loading())
    }
    .catch { throwable ->
        Timber.d("TEST_AM REQUEST_UI_STATE ERROR $throwable")
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
