package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.TransactionTypeNetworkDataSource
import com.awesome.manager.core.network.model.response.CurrencyNetwork
import com.awesome.manager.core.network.model.response.TransactionTypeNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorTransactionTypeNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionTypeNetworkDataSource {

    override suspend fun returnUpdatedTransactionsTypes(createdAt: String): List<TransactionTypeNetworkResponse> =
        httpClient.get(TransactionType.Get(createdAt = "gt.$createdAt")).body()

}


@Resource("rest/v1/transactions_type")
private data object TransactionType {

    @Resource("")
    data class Get(
        @SerialName("select") val select: String = "*",
        @SerialName("created_at") val createdAt: String,
        val parent: TransactionType = TransactionType
    )

}