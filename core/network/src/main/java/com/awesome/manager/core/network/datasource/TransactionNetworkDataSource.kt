package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.TransactionNetworkRequest
import com.awesome.manager.core.network.model.TransactionNetworkResponse

interface TransactionNetworkDataSource {

    suspend fun createTransaction(transactionNetworkResponse: List<TransactionNetworkRequest>):TransactionNetworkResponse

    suspend fun returnUpdatedTransactions():List<TransactionNetworkResponse>

}