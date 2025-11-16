package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.ProcessStates
import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    fun refreshCurrencies(): Flow<ProcessStates<Unit>>

    fun returnCurrencies(): Flow<List<AmCurrency>>

}