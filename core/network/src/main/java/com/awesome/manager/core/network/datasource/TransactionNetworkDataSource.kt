package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.request.TransactionNetworkRequest
import com.awesome.manager.core.network.model.response.TransactionNetworkResponse

interface TransactionNetworkDataSource {

    suspend fun returnUpdatedTransactions(updatedAt: String): List<TransactionNetworkResponse>

    suspend fun insertTransaction(transactionNetworkRequest: TransactionNetworkRequest): List<TransactionNetworkResponse>

    suspend fun updateTransaction(transactionNetworkRequest: TransactionNetworkRequest): List<TransactionNetworkResponse>

}