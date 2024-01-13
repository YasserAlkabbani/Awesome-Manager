package com.awesome.manager.feature.transaction.transactions

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

const val TRANSACTIONS_SEARCH_KEY: String = "TRANSACTIONS_SEARCH_KEY"

class TransactionsState(
    val savedStateHandle: SavedStateHandle,
    asTransactions: Flow<String>.() -> StateFlow<List<AmTransaction>>
) {


    val searchKey: StateFlow<String> = savedStateHandle.getStateFlow(TRANSACTIONS_SEARCH_KEY, "")
    fun onUpdateSearchKey(newSearchKey: String) {
        savedStateHandle[TRANSACTIONS_SEARCH_KEY] = newSearchKey
    }

    val transactions: StateFlow<List<AmTransaction>> = searchKey.asTransactions()

    private val _transactionNavigation: MutableStateFlow<AmTransaction?> = MutableStateFlow(null)
    val transactionNavigation: StateFlow<AmTransaction?> = _transactionNavigation
    fun navigationToTransactionNavigation(transaction: AmTransaction) {
        _transactionNavigation.update { transaction }
    }

    fun doneTransactionNavigation() {
        _transactionNavigation.update { null }
    }

    private val _profileBottomSheet:MutableStateFlow<Boolean> = MutableStateFlow(false)
    val profileBottomSheet:StateFlow<Boolean> =_profileBottomSheet
    fun showProfileBottomSheet(){_profileBottomSheet.update { true }}
    fun doneProfileBottomSheet(){_profileBottomSheet.update { false }}

}