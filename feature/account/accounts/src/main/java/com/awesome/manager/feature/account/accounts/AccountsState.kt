package com.awesome.manager.feature.account.accounts

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AccountsState(
    private val savedStateHandle: SavedStateHandle,
    coroutineScope: CoroutineScope,
    amAccountsAsStateFlow:Flow<List<AmAccount>>
){
    val accounts:StateFlow<List<AmAccount>> =amAccountsAsStateFlow
        .stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(5000), initialValue = listOf())

    private val _createAccountNavigation:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val createAccountNavigation:StateFlow<Unit?> =_createAccountNavigation
    fun startCreateAccountNavigation(){_createAccountNavigation.update {  }}
    fun doneCreateAccountNavigation(){_createAccountNavigation.update { null }}

    private val _accountDetailsNavigation:MutableStateFlow<String?> =MutableStateFlow(null)
    val accountDetailsNavigation:StateFlow<String?> =_accountDetailsNavigation
    fun startAccountDetailsNavigation(accountId:String){_accountDetailsNavigation.update { accountId }}
    fun doneAccountDetailsNavigation(){_accountDetailsNavigation.update { null }}

    private val _createTransactionNavigation:MutableStateFlow<String?> =MutableStateFlow(null)
    val createTransactionNavigation:StateFlow<String?> =_createTransactionNavigation
    fun startCreateTransactionNavigation(accountId:String?){_createTransactionNavigation.update { accountId }}
    fun doneCreateTransactionNavigation(){_createTransactionNavigation.update { null }}

}