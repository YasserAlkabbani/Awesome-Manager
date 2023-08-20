package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.resources.Resource
import javax.inject.Inject

@Resource("rest/v1/transactions")
private class TransactionRequest {
    @Resource("")
    class ReturnTransactions(
        val parent: TransactionRequest = TransactionRequest(),
        val select: String = "*"
    )
}

class KtorTransactionNetworkDataSource @Inject constructor(private val httpClient: HttpClient) :
    TransactionNetworkDataSource {

    override suspend fun createTransaction(transactionNetworkResponse: List<TransactionNetworkRequest>): TransactionNetworkResponse =
        httpClient.post(TransactionRequest.ReturnTransactions()).asResult()

    override suspend fun returnUpdatedTransactions(): List<TransactionNetworkResponse> =
        httpClient.get(TransactionRequest.ReturnTransactions()).asResult()

}