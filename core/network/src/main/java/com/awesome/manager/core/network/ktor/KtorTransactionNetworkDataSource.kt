package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorTransactionNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionNetworkDataSource {

    override suspend fun upsertTransaction(transactionNetworkResponse: TransactionNetworkRequest) =
        httpClient.post(UpsertTransaction) {
            header("Prefer", "resolution=merge-duplicates")
            setBody(transactionNetworkResponse)
        }.asResult<Any>()

    override suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse> =
        httpClient.get(GetTransactions(updatedAt = "gt.$updatedAt"))
            .asResult()

}

private const val TRANSACTION_URL:String="rest/v1/transactions"

@Resource(TRANSACTION_URL)
private class UpsertTransaction

@Resource(TRANSACTION_URL)
private class GetTransactions(
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("select") val select: String = "*"
)