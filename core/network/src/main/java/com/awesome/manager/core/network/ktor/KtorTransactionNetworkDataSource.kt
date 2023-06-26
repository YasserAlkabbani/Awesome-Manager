package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.TransactionNetworkDataSource
import com.awesome.manager.core.network.model.TransactionNetwork
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.get
import io.ktor.client.request.post
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

    override suspend fun createTransaction(transactionNetwork: TransactionNetwork): TransactionNetwork =
        httpClient.post(TransactionRequest.ReturnTransactions()).asResult()

    override suspend fun returnUpdatedTransactions(): List<TransactionNetwork> =
        httpClient.get(TransactionRequest.ReturnTransactions()).asResult()

}