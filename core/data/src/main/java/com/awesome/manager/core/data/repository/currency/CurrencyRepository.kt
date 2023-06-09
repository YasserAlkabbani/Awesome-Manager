package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.model.AmCurrency
import kotlinx.coroutines.flow.Flow

interface  CurrencyRepository{

    suspend fun refreshCurrency()

    fun returnCurrencies():Flow<List<AmCurrency>>

}