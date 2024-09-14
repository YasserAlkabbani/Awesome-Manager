package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.model.request.Currency
import com.awesome.manager.core.network.model.response.CurrencyNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import javax.inject.Inject

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    CurrencyNetworkDataSource {

    override suspend fun returnUpdatedCurrency(updatedAt: String): List<CurrencyNetwork> =
        httpClient
            .get(Currency.Get(updatedAt = "gt.$updatedAt")).body()

}