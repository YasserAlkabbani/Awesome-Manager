package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.model.CurrencyNetwork
import com.awesome.manager.core.network.model.asResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.post
import io.ktor.resources.Resource
import javax.inject.Inject

@Resource("rest/v1/currencies?select=*")
private class CurrencyRequest

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient):CurrencyNetworkDataSource{

    override suspend fun returnUpdatedCurrency(): List<CurrencyNetwork> =httpClient.post(CurrencyRequest()).asResult()

}