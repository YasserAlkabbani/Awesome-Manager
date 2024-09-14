package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.response.CurrencyNetwork

interface CurrencyNetworkDataSource {

    suspend fun returnUpdatedCurrency(updatedAt: String): List<CurrencyNetwork>

}