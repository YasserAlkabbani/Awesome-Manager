package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.amRequest
import com.awesome.manager.core.common.asDateTime
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.CurrencyDao
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCurrencyRepository @Inject constructor(
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val currencyDao: CurrencyDao,
) : CurrencyRepository {

    override suspend fun refreshCurrency() {
        amRequest {
            val lastUpdateCurrencyTime =
                (currencyDao.returnLastUpdatedCurrencyType()?.updatedAt ?: 0) + 1
            val lastUpdatedCurrencyDateTime = lastUpdateCurrencyTime.asDateTime().toString()
            val currencies = currencyNetworkDataSource
                .returnUpdatedCurrency(lastUpdatedCurrencyDateTime).map { it.asEntity() }
            currencyDao.upsertCurrency(currencies)
        }.collect()
    }

    override fun returnCurrencies(): Flow<List<AmCurrency>> =
        currencyDao.returnCurrencies().map { it.map { it.asModel() } }

    override fun returnCurrencyById(currencyId: String): Flow<AmCurrency> =
        currencyDao.returnCurrencyById(currencyId).map { it.asModel() }

}