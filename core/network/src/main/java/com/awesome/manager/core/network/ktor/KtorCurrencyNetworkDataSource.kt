package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.model.response.CurrencyNetwork
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    CurrencyNetworkDataSource {

    override suspend fun returnUpdatedCurrencies(createdAt: String): List<CurrencyNetwork> =
        httpClient.get(Currency.Get(createdAt = "gt.$createdAt")).body()

}


@Resource("rest/v1/currencies")
data object Currency {

    @Resource("")
    data class Get(
        @SerialName("select") val select: String = "*",
        @SerialName("created_at") val createdAt: String,
        val parent: Currency = Currency
    )

}