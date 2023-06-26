package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TransactionState(
    savedStateHandle: SavedStateHandle,
    coroutineScope: CoroutineScope,
    transactionsAsFlow:Flow<List<AmTransaction>>
) {

    val transactions:StateFlow<List<AmTransaction>> =transactionsAsFlow.stateIn(coroutineScope, SharingStarted.WhileSubscribed(5000), listOf())

}