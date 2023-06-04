package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.CurrencyNetwork

interface CurrencyNetworkDataSource {

    suspend fun returnUpdatedCurrency():List<CurrencyNetwork>

}