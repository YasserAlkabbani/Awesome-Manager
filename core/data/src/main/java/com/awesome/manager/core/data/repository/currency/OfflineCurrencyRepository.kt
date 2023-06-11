package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.CurrencyDao
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class OfflineCurrencyRepository @Inject constructor (
    private val currencyNetworkDataSource:CurrencyNetworkDataSource,
    private val currencyDao:CurrencyDao,
) :CurrencyRepository{

    override suspend fun refreshCurrency() {
        amRequest {
            val currencies=currencyNetworkDataSource.returnUpdatedCurrency().map { it.asEntity() }
            currencyDao.upsertCurrency(currencies)
        }.collect()
    }

    override fun returnCurrencies(): Flow<List<AmCurrency>> =
        currencyDao.returnCurrencies().map { it.map { it.asModel() } }

}