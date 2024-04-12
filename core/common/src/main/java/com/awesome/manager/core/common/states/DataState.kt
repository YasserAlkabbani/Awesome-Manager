package com.awesome.manager.core.common.states

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

sealed class DataState<out T> {
    data object Loading : DataState<Nothing>()
    data object Error : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
}

fun <T> MutableStateFlow<DataState<T>>.setData(data: () -> T) =
    update { DataState.Success(data()) }

fun <T> MutableStateFlow<DataState<T>>.updateData(newData: (T) -> T) =
    update {
        if (it is DataState.Success) it.copy(newData(it.data))
        else it
    }

fun <T> Flow<DataState<T>>.asDataStateFlow(scope: CoroutineScope): StateFlow<DataState<T>> =
    flowOn(Dispatchers.Default)
        .stateIn(
            scope = scope, started = SharingStarted.Eagerly, initialValue = DataState.Loading
        )

fun <T> Flow<List<T>>.asListDataStateFlow(scope: CoroutineScope): StateFlow<List<T>> =
    flowOn(Dispatchers.Default).stateIn(
        scope = scope, started = SharingStarted.Eagerly, initialValue = listOf()
    )