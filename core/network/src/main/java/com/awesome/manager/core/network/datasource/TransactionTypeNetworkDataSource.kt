package com.awesome.manager.core.network.datasource

import com.awesome.manager.core.network.model.TransactionTypesNetwork

interface TransactionTypeNetworkDataSource {

    suspend fun returnUpdatedTransactionType(updatedAt: String): List<TransactionTypesNetwork>

}