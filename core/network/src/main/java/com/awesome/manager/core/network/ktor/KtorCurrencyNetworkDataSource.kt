package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.CurrencyNetworkDataSource
import com.awesome.manager.core.network.model.CurrencyNetwork
import com.awesome.manager.core.network.asResult
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    CurrencyNetworkDataSource {

    override suspend fun returnUpdatedCurrency(updatedAt: String): List<CurrencyNetwork> =
        httpClient.get(GetCurrency(updatedAt = "gt.$updatedAt")).asResult()

}


private const val CURRENCY_URL:String="rest/v1/currencies"


@Resource(CURRENCY_URL)
private class GetCurrency(
    @SerialName("select") val select: String = "*",
    @SerialName("updated_at") val updatedAt: String
)