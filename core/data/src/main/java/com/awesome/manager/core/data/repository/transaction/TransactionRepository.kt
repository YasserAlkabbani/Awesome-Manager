package com.awesome.manager.core.data.repository.transaction

import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(upsertTransaction: UpsertTransaction)

    fun returnTransactions(searchKey: String): Flow<List<AmTransaction>>

    fun returnTransactionsByAccountId(
        accountId: String, searchKey: String
    ): Flow<List<AmTransaction>>

    fun returnTransactionById(transactionId: String): Flow<AmTransaction>

    suspend fun refreshTransactions()

    suspend fun synTransactions()

    suspend fun deleteTransactions()

}