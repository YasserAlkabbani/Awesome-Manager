package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AccountDetailsState(
    val amAccount: StateFlow<AmAccount?>,
    val amTransactions: StateFlow<List<AmTransaction>>
) {

    private val _backNavigation: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val backNavigation: StateFlow<Boolean> = _backNavigation
    fun startBackNavigation() {
        _backNavigation.update { true }
    }

    fun doneBackNavigation() {
        _backNavigation.update { false }
    }

    private val _editAccountNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val editAccountNavigation: StateFlow<AmAccount?> = _editAccountNavigation
    fun startEditAccountNavigation() {
        amAccount.value?.let { account -> _editAccountNavigation.update { account } }
    }

    fun doneEditAccountNavigation() {
        _editAccountNavigation.update { null }
    }

    private val _createTransactionNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val createTransactionNavigation: StateFlow<AmAccount?> = _createTransactionNavigation
    fun startCreateTransactionNavigation(amAccount: AmAccount) {
        _createTransactionNavigation.update { amAccount }
    }

    fun doneCreateTransactionNavigation() {
        _createTransactionNavigation.update { null }
    }

    private val _transactionNavigation: MutableStateFlow<AmTransaction?> = MutableStateFlow(null)
    val transactionNavigation: StateFlow<AmTransaction?> = _transactionNavigation
    fun startTransactionNavigation(amTransaction: AmTransaction) {
        _transactionNavigation.update { amTransaction }
    }

    fun doneTransactionNavigation() {
        _transactionNavigation.update { null }
    }


}