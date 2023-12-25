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

@Resource("rest/v1/currencies")
private class CurrencyRequest {
    @Resource("")
    class GetCurrency(
        val select: String = "*",
        @SerialName("updated_at") val updatedAt: String,
        val perant: CurrencyRequest = CurrencyRequest()
    )
}

class KtorCurrencyNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    CurrencyNetworkDataSource {

    override suspend fun returnUpdatedCurrency(updatedAt: String): List<CurrencyNetwork> =
        httpClient.get(CurrencyRequest.GetCurrency(updatedAt = "gt.$updatedAt")).asResult()

}