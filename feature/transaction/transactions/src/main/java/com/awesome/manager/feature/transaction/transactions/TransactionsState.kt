package com.awesome.manager.feature.transaction.transactions

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction


sealed interface TransactionsState

sealed interface TransactionsEvents {
    data object Idle : TransactionsEvents
    data object CreateTransactionNavigation : TransactionsEvents
    data class TransactionDetailsNavigation(val transaction: AmTransaction) : TransactionsEvents
}