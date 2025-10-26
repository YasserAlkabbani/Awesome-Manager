package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.request.TransactionNetworkRequest
import com.awesome.manager.core.network.model.response.TransactionNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.patch
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.HttpHeaders
import io.ktor.resources.Resource
import kotlinx.serialization.SerialName
import javax.inject.Inject

class KtorTransactionNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionNetworkDataSource {

    override suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse> =
        httpClient.get(Transaction.Get(updatedAt = "gt.$updatedAt")).body()

    override suspend fun upsertTransaction(transactionNetworkRequest: TransactionNetworkRequest): Unit =
        httpClient.post(Transaction.Upsert()) {
            header(HttpHeaders.Prefer, "resolution=merge-duplicates")
            setBody(transactionNetworkRequest)
        }.body()

}


@Resource("rest/v1/transactions")
data object Transaction {

    @Resource("")
    class Get(
        @SerialName("updated_at") val updatedAt: String,
        @SerialName("parent") val parent: Transaction = Transaction,
        @SerialName("select") val select: String = "*",
    )

    @Resource("")
    class Upsert(
        @SerialName("parent") val parent: Transaction = Transaction,
    )

}