package com.awesome.manager.core.common.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

sealed class DataState<out T>{
    data object Loading: DataState<Nothing>()
    data object Error: DataState<Nothing>()
    data class Success<T>(val data:T): DataState<T>()
}

fun <T> Flow<DataState<T>>.asDataStateFlow(scope: CoroutineScope): StateFlow<DataState<T>> =flowOn(
    Dispatchers.Default)
    .stateIn(
        scope = scope, started = SharingStarted.Eagerly,
        initialValue = DataState.Loading
    )