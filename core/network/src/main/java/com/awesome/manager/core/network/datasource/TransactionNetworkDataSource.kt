package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse

interface TransactionNetworkDataSource {

    suspend fun upsertTransaction(transactionNetworkResponse: TransactionNetworkRequest): Any

    suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse>

}