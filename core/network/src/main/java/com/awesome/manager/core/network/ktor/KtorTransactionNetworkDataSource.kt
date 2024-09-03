package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorTransactionNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionNetworkDataSource {

    override suspend fun upsertTransaction(transactionNetworkResponse: TransactionNetworkRequest):Unit =
        httpClient
            .post(UpsertTransaction()) {
                header("Prefer", "resolution=merge-duplicates")
                setBody(transactionNetworkResponse)
            }
            .body()

    override suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse> =
        httpClient
            .get(GetTransactions(updatedAt = "gt.$updatedAt"))
            .body()

}

@Resource("rest/v1/transactions")
private data object TransactionRequest

private class UpsertTransaction(
    @SerialName("parent") val parent: TransactionRequest = TransactionRequest,
)

private class GetTransactions(
    @SerialName("parent") val parent: TransactionRequest = TransactionRequest,
    @SerialName("updated_at") val updatedAt: String,
    @SerialName("select") val select: String = "*"
)