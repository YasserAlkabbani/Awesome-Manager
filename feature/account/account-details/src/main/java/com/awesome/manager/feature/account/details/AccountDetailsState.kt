package com.awesome.manager.feature.account.details

import androidx.lifecycle.SavedStateHandle
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AccountDetailsState(
    private val savedStateHandle: SavedStateHandle,
    private val defaultCurrency:AmCurrency,
    private val returnCurrencyByCurrencyId:(String)-> AmCurrency,
    coroutineScope: CoroutineScope,
    amAccountAsFlow: Flow<AmAccount?>,
    amAccountsAsFlow: Flow<List<AmAccount>>
){


}