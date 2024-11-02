package com.awesome.manager.core.ui.actions

import com.awesome.manager.core.common.AmUIState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

fun <T> MutableStateFlow<AmUIState<T>>.setData(data: () -> T) =
    update { AmUIState.Success(data()) }

fun <T> MutableStateFlow<AmUIState<T>>.updateData(newData: (T) -> T) =
    update {
        if (it is AmUIState.Success) it.copy(newData(it.data))
        else it
    }

//fun <T> Flow<AmUIState<T>>.asUIState(scope: CoroutineScope): StateFlow<AmUIState<T>> =
//    flowOn(Dispatchers.Default)
//        .stateIn(
//            scope = scope,
//            started = SharingStarted.WhileSubscribed(10000),
//            initialValue = AmUIState.Loading()
//        )

fun <T> Flow<T>.asUIState(scope: CoroutineScope): StateFlow<AmUIState<T>> =
    map { AmUIState.Success(it) }
        .flowOn(Dispatchers.Default)
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(10000),
            initialValue = AmUIState.Loading()
        )

//fun <T> Flow<AmUIState<List<T>>>.asListDataStateFlow(scope: CoroutineScope): StateFlow<AmUIState<List<T>>> =
//    flowOn(Dispatchers.Default).stateIn(
//        scope = scope, started = SharingStarted.WhileSubscribed(10000), initialValue = AmUIState.Loading()
//    )