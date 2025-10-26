package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.response.CurrencyNetwork
import com.awesome.manager.core.network.model.response.TransactionTypeNetworkResponse

interface TransactionTypeNetworkDataSource {

    suspend fun returnUpdatedTransactionsTypes(createdAt: String): List<TransactionTypeNetworkResponse>

}