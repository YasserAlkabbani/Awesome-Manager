package com.awesome.manager.feature.account.search

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AccountsState(
    private val savedStateHandle: SavedStateHandle,
    coroutineScope: CoroutineScope,
    amAccountsAsFlow:Flow<List<AmAccount>>
){
    val accounts:StateFlow<List<AmAccount>> =amAccountsAsFlow
        .stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(5000), initialValue = listOf())

    private val _createAccountAction:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val createAccountAction:StateFlow<Unit?> =_createAccountAction
    fun startCreateAccountAction(){_createAccountAction.update {  }}
    fun doneCreateAccountAction(){_createAccountAction.update { null }}
}