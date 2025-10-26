package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.extention.amRequest
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.CurrencyDao
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCurrencyRepository @Inject constructor(
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val currencyDao: CurrencyDao
): CurrencyRepository{
    override fun refreshCurrencies(): Flow<AmState<Unit>> = amRequest{
        val currenciesEntity=
            currencyNetworkDataSource
            .returnUpdatedCurrencies(0L.asDateTimeString())
            .map { it.asEntity() }
        currencyDao.upsertCurrencies(currenciesEntity)
    }

    override fun returnCurrencies(): Flow<List<AmCurrency>> =
        currencyDao.returnCurrencies().map { it.map { it.asModel() } }
}