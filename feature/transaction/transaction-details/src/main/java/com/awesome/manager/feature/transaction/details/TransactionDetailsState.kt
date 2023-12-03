package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TransactionDetailsState(
    val transaction: StateFlow<AmTransaction?>,
    asAccount: StateFlow<AmTransaction?>.() -> StateFlow<AmAccount?>
) {

    val account: StateFlow<AmAccount?> = transaction.asAccount()

    private val _accountNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val accountNavigation: StateFlow<AmAccount?> = _accountNavigation
    fun startAccountNavigation(amAccount: AmAccount) {
        _accountNavigation.update { amAccount }
    }

    fun doneAccountNavigation() {
        _accountNavigation.update { null }
    }

    private val _createTransactionNavigation: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val createTransactionNavigation: StateFlow<AmAccount?> = _createTransactionNavigation
    fun startCreateTransactionNavigation(amAccount: AmAccount) {
        _createTransactionNavigation.update { amAccount }
    }

    fun doneCreateTransactionNavigation() {
        _createTransactionNavigation.update { null }
    }

    private val _editTransactionNavigation: MutableStateFlow<AmTransaction?> =
        MutableStateFlow(null)
    val editTransactionNavigation: StateFlow<AmTransaction?> = _editTransactionNavigation
    fun startEditTransactionNavigation(amTransaction: AmTransaction?) {
        _editTransactionNavigation.update { amTransaction }
    }

    fun doneEditTransactionNavigation() {
        _editTransactionNavigation.update { null }
    }

}