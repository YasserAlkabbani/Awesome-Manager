package com.awesome.manager.core.network.ktor

import com.awesome.manager.core.network.asResult
import com.awesome.manager.core.network.datasource.TransactionTypeNetworkDataSource
import com.awesome.manager.core.network.model.TransactionTypesNetwork
import io.ktor.client.HttpClient
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.get
import io.ktor.resources.Resource
import javax.inject.Inject

@Resource("rest/v1/transaction_types")
private class TransactionTypeRequest {
    @Resource("")
    class ReturnAccount(
        val parent: TransactionTypeRequest = TransactionTypeRequest(),
        val select: String = "*"
    )
}

class KtorTransactionTypeNetworkDataSource @Inject constructor(private val httpClient: HttpClient):TransactionTypeNetworkDataSource {

    override suspend fun returnUpdatedTransactionType(): List<TransactionTypesNetwork> =httpClient.get(
        TransactionTypeRequest.ReturnAccount()
    ).asResult()

}