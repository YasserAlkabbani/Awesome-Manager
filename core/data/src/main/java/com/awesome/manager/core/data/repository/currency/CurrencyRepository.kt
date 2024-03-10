package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.database.model.CurrencyEntityWithData
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.CurrencyWithBalance
import kotlinx.coroutines.flow.Flow

interface CurrencyRepository {

    suspend fun refreshCurrency()

    fun returnCurrencies(): Flow<List<AmCurrency>>

    fun returnCurrencyById(currencyId: String): Flow<AmCurrency>

    fun returnCurrenciesBalance(): Flow<List<CurrencyWithBalance>>

}