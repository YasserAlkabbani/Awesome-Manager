package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AccountDetailsState(
    savedStateHandle: SavedStateHandle,
    val amAccount: StateFlow<AmAccount?>,
    val amTransactions:StateFlow<List<AmTransaction>>
){

    private val _backNavigation:MutableStateFlow<Unit?> = MutableStateFlow(null)
    val backNavigation:StateFlow<Unit?> =_backNavigation
    fun startBackNavigation(){_backNavigation.update {  }}
    fun doneBackNavigation(){_backNavigation.update { null }}

    private val _transactionNavigation:MutableStateFlow<String?> = MutableStateFlow(null)
    val transactionNavigation:StateFlow<String?> =_transactionNavigation
    fun startTransactionNavigation(transactionId:String){_transactionNavigation.update { transactionId }}
    fun doneTransactionNavigation(){_transactionNavigation.update { null }}

}