package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.AmTransactionWithDetails
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(transaction: AmTransaction)

    fun getTransactions(
        searchKey: String? = "",
        transactionType: AmTransactionType? = null,
        fromDate: Long? = null,
        toDate: Long? = null,
    ): Flow<PagingData<AmTransactionWithDetails>>

    fun getTransactionsByAccountID(
        accountId: String, searchKey: String,
    ): Flow<PagingData<AmTransactionWithDetails>>

    fun returnTransactionByID(transactionId: String): Flow<AmTransactionWithDetails>

    fun refreshTransactions(): Flow<AmState<Unit>>

    fun synTransactions(): Flow<AmState<Unit>>

    suspend fun getTransactionCount(accountId: String): Int

    suspend fun deleteTransactions()

}