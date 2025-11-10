package com.awesome.manager.feature.transaction.transactions


sealed interface TransactionsState {
    data object Idle : TransactionsState
    data object Loading : TransactionsState
    data object Error : TransactionsState
}

sealed interface TransactionsEvents {
    data object Idle : TransactionsEvents
    data object NavigationCreateTransaction : TransactionsEvents
    data class NavigationTransactionDetails(val accountID:String, val transactionID:String) : TransactionsEvents
}