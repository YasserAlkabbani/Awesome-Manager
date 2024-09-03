package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.model.CurrencyNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.parameter
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    CurrencyNetworkDataSource {

        private fun getCurrencyUrl(path:String="")="rest/v1/currencies/$path"

    override suspend fun returnUpdatedCurrency(updatedAt: String): List<CurrencyNetwork> =
        httpClient
            .get(getCurrencyUrl()){
                parameter("select","*")
                parameter("updated_at","gt.$updatedAt")
            }.body()

}