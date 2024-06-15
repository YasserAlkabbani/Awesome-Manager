package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(upsertTransaction: UpsertTransaction)

    fun returnTransactions(
        searchKey: String?, transactionType: AmTransactionType?,
        fromDate: Long?, toDate: Long?
    ): Flow<PagingData<AmTransaction>>

    fun returnTransactionsByAccountId(
        accountId: String, searchKey: String
    ): Flow<PagingData<AmTransaction>>

    fun returnTransactionById(transactionId: String): Flow<AmTransaction>

    suspend fun refreshTransactions()

    suspend fun synTransactions()

    suspend fun returnTransactionCount(accountId: String): Int

    suspend fun deleteTransactions()

}