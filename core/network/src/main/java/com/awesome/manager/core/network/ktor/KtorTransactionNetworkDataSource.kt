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

@Resource("rest/v1/transactions")
private class TransactionRequest {
    @Resource("")
    class ReturnTransactions(
        val parent: TransactionRequest = TransactionRequest(),
        @SerialName("updated_at") val updatedAt: String,
        val select: String = "*"
    )
}

class KtorTransactionNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionNetworkDataSource {

    override suspend fun upsertTransaction(transactionNetworkResponse: TransactionNetworkRequest) =
        httpClient.post(TransactionRequest()) {
            header("Prefer", "resolution=merge-duplicates")
            setBody(transactionNetworkResponse)
        }.asResult<Any>()

    override suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse> =
        httpClient.get(TransactionRequest.ReturnTransactions(updatedAt = "gt.$updatedAt"))
            .asResult()

}