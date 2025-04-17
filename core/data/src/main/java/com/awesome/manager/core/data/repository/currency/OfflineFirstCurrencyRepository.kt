package com.awesome.manager.core.data.repository.currency

import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.data.extention.requestUIState
import com.awesome.manager.core.common.asDateTimeString
import com.awesome.manager.core.data.model.asEntity
import com.awesome.manager.core.data.model.asModel
import com.awesome.manager.core.database.dao.CurrencyDao
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.BalanceDetails
import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class OfflineFirstCurrencyRepository @Inject constructor(
    private val currencyNetworkDataSource: CurrencyNetworkDataSource,
    private val currencyDao: CurrencyDao,
) : CurrencyRepository {

    override fun refreshCurrencies(): Flow<AmState<List<AmCurrency>>> =
        requestUIState {
            val lastUpdateCurrencyTime =
                (currencyDao.returnLastUpdatedCurrencyType()?.updatedAt ?: 0) + 1
            val lastUpdatedCurrencyDateTime = lastUpdateCurrencyTime.asDateTimeString()

            val currenciesNetwork = currencyNetworkDataSource
                .returnUpdatedCurrency(lastUpdatedCurrencyDateTime)
            val currenciesEntity = currenciesNetwork.map { it.asEntity() }

            currenciesEntity.map { it.upsert() }
        }

    override fun returnCurrencies(): Flow<List<AmCurrency>> =
        currencyDao.returnCurrencies().map { it.map { it.asModel() } }

    override fun returnBalanceDetails(): Flow<List<BalanceDetails>> =
        currencyDao.returnCurrenciesBalance().map { it.map { it.asModel() } }

    private suspend fun CurrencyEntity.upsert(): AmCurrency {
        currencyDao.upsertCurrency(this)
        return currencyDao.getCurrencyByID(id).first().asModel()
    }

}