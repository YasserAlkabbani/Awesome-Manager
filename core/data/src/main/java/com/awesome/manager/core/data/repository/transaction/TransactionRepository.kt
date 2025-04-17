package com.awesome.manager.core.data.repository.transaction

import androidx.paging.PagingData
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.model.AmTransaction
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.model.UpsertTransaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun upsertTransaction(upsertTransaction: UpsertTransaction)

    fun getTransactions(
        searchKey: String? = "",
        transactionType: AmTransactionType? = null,
        fromDate: Long? = null,
        toDate: Long? = null,
    ): Flow<PagingData<AmTransaction>>

    fun getTransactionsByAccountID(
        accountId: String, searchKey: String,
    ): Flow<PagingData<AmTransaction>>

    fun getTransactionById(transactionId: String): Flow<AmTransaction>

    fun refreshTransactions(): Flow<AmState<List<AmTransaction>>>

    fun synTransactions(): Flow<AmState<List<AmTransaction>>>

    suspend fun getTransactionCount(accountId: String): Int

    suspend fun deleteTransactions()

}