package com.awesome.manager.core.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

//fun <T> MutableStateFlow<AmState<T>>.setData(data: () -> T) =
//    update { AmState.Success(data()) }
//
//fun <T> MutableStateFlow<AmState<T>>.updateData(newData: (T) -> T) =
//    update {
//        if (it is AmState.Success) it.copy(newData(it.data))
//        else it
//    }

//fun <T> Flow<AmUIState<T>>.asUIState(scope: CoroutineScope): StateFlow<AmUIState<T>> =
//    flowOn(Dispatchers.Default)
//        .stateIn(
//            scope = scope,
//            started = SharingStarted.WhileSubscribed(10000),
//            initialValue = AmUIState.Loading()
//        )

fun <T> Flow<T>.asAmState(scope: CoroutineScope): StateFlow<AmState<T>> =
    map { it -> AmState.Success(it) }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(60000),
            initialValue = AmState.Loading()
        )

fun <T> StateFlow<AmState<T>>.filterSuccess() =
    filterIsInstance<AmState.Success<T>>()
        .map { it.data }


fun <T> Flow<T?>.asStateFlow(scope: CoroutineScope, initValue: T?): StateFlow<T?> =
    stateIn(
        scope = scope,
        initialValue = initValue,
        started = SharingStarted.WhileSubscribed(60000)
    )

fun <T> AmState<T>.dataOrNull() = when (this) {
    is AmState.Success -> data
    else -> null
}