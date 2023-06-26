package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.TransactionNetwork

interface TransactionNetworkDataSource {

    suspend fun createTransaction(transactionNetwork: TransactionNetwork):TransactionNetwork

    suspend fun returnUpdatedTransactions():List<TransactionNetwork>

}