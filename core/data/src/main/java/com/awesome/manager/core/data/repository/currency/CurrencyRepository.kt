package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.BalanceDetails
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun refreshCurrency(): Flow<AmUIState<Unit>>

    fun returnCurrencies(): Flow<List<AmCurrency>>

    fun returnBalanceDetails(): Flow<List<BalanceDetails>>

}