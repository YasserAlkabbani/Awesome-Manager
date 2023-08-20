package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

const val TRANSACTIONS_SEARCH_KEY:String="TRANSACTIONS_SEARCH_KEY"

class TransactionsState(
    val savedStateHandle: SavedStateHandle,
    asTransactions:Flow<String>.()->StateFlow<List<AmTransaction>>
) {


    val searchKey:StateFlow<String> =savedStateHandle.getStateFlow(TRANSACTIONS_SEARCH_KEY,"")
    fun onUpdateSearchkey(newSearchKey:String){savedStateHandle[TRANSACTIONS_SEARCH_KEY]=newSearchKey}

    val transactions:StateFlow<List<AmTransaction>> =searchKey.asTransactions()

    private val _transactionNavigation:MutableStateFlow<String?> =MutableStateFlow(null)
    val transactionNavigation:StateFlow<String?> =_transactionNavigation
    fun navigationToTransactionNavigation(transactionId:String){_transactionNavigation.update { transactionId }}
    fun doneTransactionNavigation(){_transactionNavigation.update { null }}

}